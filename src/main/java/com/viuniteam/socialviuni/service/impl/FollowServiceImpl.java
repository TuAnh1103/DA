package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.follow.FollowResponse;
import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.entity.Following;
import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.JsonException;
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
    public ResponseEntity<?> addFollow(Long idTarget) {
        Long idSource = profile.getId();
        if(friendService.itIsMe(profile.getId(),idTarget))
            return new ResponseEntity<>(new JsonException(400,"Không thể follow chính mình"), HttpStatus.BAD_REQUEST);
        if(this.isFollowing(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400,"Đã follow rồi"), HttpStatus.BAD_REQUEST);

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

        return new ResponseEntity<>(new JsonException(200,"Follow thành công"),HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<?> removeFollow(Long idTarget) {
        Long idSource = profile.getId();
        if(friendService.itIsMe(profile.getId(),idTarget))
            return new ResponseEntity<>(new JsonException(400,"Không thể hủy follow chính mình"), HttpStatus.BAD_REQUEST);
        if(!this.isFollowing(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400,"Chưa follow"), HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(new JsonException(200,"Hủy follow thành công"),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllFollower(Long id) {
        User user = userService.findOneById(id);
        if(user == null)
            return new ResponseEntity<>(new JsonException(404,"Người dùng không tồn tại"),HttpStatus.NOT_FOUND);

        List<Follower> followerList = user.getFollowers();
        List<FollowResponse> followResponseList = new ArrayList<>();
        followerList.forEach(follower -> {
            FollowResponse followResponse = followerResponseMapper.from(follower);
            followResponse.setUserInfoResponse(userInfoResponseMapper.from(follower.getUser()));
            followResponseList.add(followResponse);
        });
        return new ResponseEntity<>(followResponseList,HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> getAllFollowing(Long id) {
        User user = userService.findOneById(id);
        if(user == null)
            return new ResponseEntity<>(new JsonException(404,"Người dùng không tồn tại"),HttpStatus.NOT_FOUND);

        List<Following> followingList = user.getFollowings();
        List<FollowResponse> followResponseList = new ArrayList<>();
        followingList.forEach(following -> {
            FollowResponse followResponse = followingResponseMapper.from(following);
            followResponse.setUserInfoResponse(userInfoResponseMapper.from(following.getUser()));
            followResponseList.add(followResponse);
        });
        return new ResponseEntity<>(followResponseList,HttpStatus.ACCEPTED);
    }


    @Override
    public boolean isFollower(Long idSource, Long idTarget) {
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);
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
        List<Following> followingSourceList = userSource.getFollowings();
        for(Following following : followingSourceList)
            if(userTarget.getId() == following.getUser().getId())
                return true;
        return false;
    }
}
