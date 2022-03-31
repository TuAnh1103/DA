package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.follow.FollowResponse;
import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.entity.Following;
import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.response.follow.FollowerResponseMapper;
import com.viuniteam.socialviuni.mapper.response.follow.FollowingResponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserInfoResponseMapper;
import com.viuniteam.socialviuni.repository.FollowerRepository;
import com.viuniteam.socialviuni.repository.FollowingRepository;
import com.viuniteam.socialviuni.service.FollowService;
import com.viuniteam.socialviuni.service.FriendService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final Profile profile;
    private final FollowerRepository followerRepository;
    private final FollowingRepository followingRepository;
    private final UserService userService;
    private final FriendService friendService;
    private final UserInfoResponseMapper userInfoResponseMapper;
    private final FollowerResponseMapper followerResponseMapper;
    private final FollowingResponseMapper followingResponseMapper;
    @Override
    public void addFollow(Long idTarget) {
        Long idSource = profile.getId();
        if(friendService.itIsMe(profile.getId(),idTarget))
            throw new BadRequestException("Không thể follow chính mình");
        if(this.isFollowing(idSource,idTarget))
            throw new BadRequestException("Đã follow rồi");

        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);

        List<Following> followingSourceList = userSource.getFollowings();
        List<Follower> followerTargetList = userTarget.getFollowers();

        Following following = new Following();
        following.setUser(userTarget);
        followingRepository.save(following);

        Follower follower = new Follower();
        follower.setUser(userSource);
        followerRepository.save(follower);


        followingSourceList.add(following);
        followerTargetList.add(follower);

        userService.update(userSource);
        userService.update(userTarget);

        throw new OKException("Follow thành công");

    }

    @Override
    public void removeFollow(Long idTarget) {
        Long idSource = profile.getId();
        if(friendService.itIsMe(profile.getId(),idTarget))
            throw new BadRequestException("Không thể hủy follow chính mình");
        if(!this.isFollowing(idSource,idTarget))
            throw new BadRequestException("Chưa follow");
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);

        List<Following> followingSourceList = userSource.getFollowings();
        List<Follower> followerTargetList = userTarget.getFollowers();


        for(Following following : followingSourceList){
            if(userTarget.getId()== following.getUser().getId()){
                followingSourceList.remove(following);
                userSource.setFollowings(followingSourceList);
                userService.update(userSource);
                followingRepository.deleteById(following.getId());
                break;
            }
        }
        for(Follower follower : followerTargetList){
            if(userSource.getId()== follower.getUser().getId()){
                followerTargetList.remove(follower);
                userTarget.setFollowers(followerTargetList);
                userService.update(userTarget);
                followerRepository.deleteById(follower.getId());
                break;
            }
        }
        throw new OKException("Hủy follow thành công");
    }

    @Override
    public List<FollowResponse> getAllFollower(Long id) {
        User user = userService.findOneById(id);
        if(user == null)
            throw new ObjectNotFoundException("Tài khoản không tồn tại");

        List<Follower> followerList = user.getFollowers();
        List<FollowResponse> followResponseList = new ArrayList<>();
        followerList.forEach(follower -> {
            FollowResponse followResponse = followerResponseMapper.from(follower);
            followResponse.setUserInfoResponse(userInfoResponseMapper.from(follower.getUser()));
            followResponseList.add(followResponse);
        });
        return followResponseList;
    }

    @Override
    public List<FollowResponse> getAllFollowing(Long id) {
        User user = userService.findOneById(id);
        if(user == null)
            throw new ObjectNotFoundException("Tài khoản không tồn tại");
        List<Following> followingList = user.getFollowings();
        List<FollowResponse> followResponseList = new ArrayList<>();
        followingList.forEach(following -> {
            FollowResponse followResponse = followingResponseMapper.from(following);
            followResponse.setUserInfoResponse(userInfoResponseMapper.from(following.getUser()));
            followResponseList.add(followResponse);
        });
        return followResponseList;
    }


    @Override
    public boolean isFollower(Long idSource, Long idTarget) {
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);
        if(userTarget==null)
            throw new ObjectNotFoundException("Tài khoản không tồn tại");
        List<Follower> followerSourceList = userSource.getFollowers();
        for(Follower follower : followerSourceList)
            if(userTarget.getId() == follower.getUser().getId())
                return true;
        return false;
    }

    @Override
    public boolean isFollowing(Long idSource, Long idTarget) {
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);
        if(userTarget==null)
            throw new ObjectNotFoundException("Tài khoản không tồn tại");
        List<Following> followingSourceList = userSource.getFollowings();
        for(Following following : followingSourceList)
            if(userTarget.getId() == following.getUser().getId())
                return true;
        return false;
    }
}
