package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.dto.utils.user.UserInfoResponseUtils;
import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.entity.mapper.UserFriends;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.response.friend.FriendResponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserInfoResponseMapper;
import com.viuniteam.socialviuni.repository.FriendRepository;
import com.viuniteam.socialviuni.repository.UserRepository;
import com.viuniteam.socialviuni.service.FriendService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;
    private final FriendResponseMapper friendResponseMapper;
    private final UserInfoResponseMapper userInfoResponseMapper;
    private final UserInfoResponseUtils userInfoResponseUtils;
    private final UserRepository userRepository;

    @Override
    public List<UserInfoResponse> listFriendSuggestions(Long userId) {
        User user = userService.findOneById(userId);
        List<UserFriends> friendList = friendRepository.findByUserOrderByIdDesc(user.getId());
        System.out.println(friendList.size());
        List<Long> userIdList = new ArrayList<>();
        userIdList.add(user.getId());
        friendList.stream().forEach(friend -> userIdList.add(friend.getUserId()));

//        String notInUserId = userIdList.toString();
//        notInUserId = notInUserId.substring(1,notInUserId.length()-1);
//        Page<User> users = userRepository.getListFriendSuggestion(notInUserId,pageable);
//        List<User> users = userRepository.getListFriendSuggestion(notInUserId);
        List<User> users = userRepository.findAll();
        List<User> usersSuggestion = users.stream().filter(user1 -> !userIdList.contains(user1.getId())).collect(Collectors.toList());
        return userInfoResponseUtils.convert(usersSuggestion);
    }

    @Override
    public void addFriend(Long idSource, Long idTarget) {

        if(this.itIsMe(idSource,idTarget))
            throw new BadRequestException("Không thể kết bạn với chính mình");

        if(this.isFriend(idSource,idTarget))
            throw new BadRequestException("Đã kết bạn rồi");
        else {
            User userSource = userService.findOneById(idSource);
            User userTarget = userService.findOneById(idTarget);


            List<Friend> friendSourceList = userSource.getFriends();
            List<Friend> friendTargetList = userTarget.getFriends();
            // add friend target to list
            Friend friendSource = new Friend();
            friendSource.setUser(userTarget);
            friendRepository.save(friendSource);
            friendSourceList.add(friendSource);
            userSource.setFriends(friendSourceList);
            userService.update(userSource);

            // add friend source to list
            Friend friendTarget = new Friend();
            friendTarget.setUser(userSource);
            friendRepository.save(friendTarget);
            friendTargetList.add(friendTarget);
            userTarget.setFriends(friendTargetList);
            userService.update(userTarget);

            /*
            // add friend target to list
            Friend friendSource = new Friend();
            friendSource.setUser(userTarget);
            Friend friendSourceSave = friendRepository.save(friendSource);
            friendRepository.insertUserFriend(userSource.getId(),friendSourceSave.getId());

            // add friend source to list
            Friend friendTarget = new Friend();
            friendTarget.setUser(userSource);
            Friend friendTargetSave = friendRepository.save(friendTarget);
            friendRepository.insertUserFriend(userTarget.getId(),friendTargetSave.getId());
            */
//            throw new OKException("Kết bạn thành công");
        }
    }

    @Override
    public void removeFriend(Long idSource, Long idTarget) {

        if(this.itIsMe(idSource,idTarget))
            throw new BadRequestException("Không thể hủy kết bạn với chính mình");
        if(this.isFriend(idSource,idTarget)){
            User userSource = userService.findOneById(idSource);
            User userTarget = userService.findOneById(idTarget);

            List<Friend> friendSourceList = userSource.getFriends();
            List<Friend> friendTargetList = userTarget.getFriends();
            for(Friend friend : friendSourceList){
                if(friend.getUser().getId() == userTarget.getId()){
                    friendSourceList.remove(friend);
                    userSource.setFriends(friendSourceList);
                    userService.update(userSource);
                    friendRepository.deleteFriendById(friend.getId());
                    break;
                }
            }
            for(Friend friend : friendTargetList){
                if(friend.getUser().getId() == userSource.getId()){
                    friendTargetList.remove(friend);
                    userTarget.setFriends(friendTargetList);
                    userService.update(userTarget);
                    friendRepository.deleteFriendById(friend.getId());
                    break;
                }
            }

            /*for(Friend friend : friendSourceList){
                if(friend.getUser().getId() == userTarget.getId()){
                    friendRepository.deleteUserFriendByUserIdAndFriendId(idSource,friend.getId());
                    friendRepository.deleteFriendById(friend.getId());
                    break;
                }
            }
            for(Friend friend : friendTargetList){
                if(friend.getUser().getId() == userSource.getId()){
                    friendRepository.deleteUserFriendByUserIdAndFriendId(idTarget,friend.getId());
                    friendRepository.deleteFriendById(friend.getId());
                    break;
                }
            }*/

            throw new OKException("Hủy kết bạn thành công");
        }
        throw new BadRequestException("Chưa kết bạn");
    }

    @Override
    public List<FriendResponse> getAll(Long id) {
        User user = userService.findOneById(id);
        if(user == null)
            throw new ObjectNotFoundException("Người dùng không tồn tại");

        List<Friend> friendList = user.getFriends();
        List<FriendResponse> friendResponseList = new ArrayList<>();
        friendList.forEach(friend -> {
            System.out.println(friend.getUser().getId());
            FriendResponse friendResponse = friendResponseMapper.from(friend);
//            friendResponse.setUserInfoResponse(userInfoResponseMapper.from(friend.getUser()));
            friendResponse.setUserInfoResponse(userInfoResponseUtils.convert(friend.getUser()));
            friendResponseList.add(friendResponse);
        });
        return friendResponseList;
    }

    @Override
    public Page<FriendResponse> getAllByUserId(Long userId, Pageable pageable) {
        User user = userService.findOneById(userId);
        if(user == null)
            throw new ObjectNotFoundException("Người dùng không tồn tại");
        /*Page<Friend> friends = friendRepository.findByUserOrderByIdDesc(user,pageable);
        List<FriendResponse> friendResponseList = new ArrayList<>();
        friends.stream().forEach(
                friend -> {
                    FriendResponse friendResponse = friendResponseMapper.from(friend);
//                    friendResponse.setUserInfoResponse(userInfoResponseMapper.from(friend.getUser()));
                    friendResponse.setUserInfoResponse(userInfoResponseUtils.convert(friend.getUser()));
                    friendResponseList.add(friendResponse);
                }
        );
        return new PageImpl<>(friendResponseList, pageable, friendResponseList.size());*/

        Page<UserFriends> friends = friendRepository.findByUserOrderByIdDesc(user.getId(),pageable);
        List<FriendResponse> friendResponseList = new ArrayList<>();
        friends.stream().forEach(
                friend -> {
                    FriendResponse friendResponse = new FriendResponse();
                    friendResponse.setId(friend.getId());
                    friendResponse.setCreatedDate(friend.getCreatedDate());
                    friendResponse.setUserInfoResponse(userInfoResponseUtils.convert(userService.findOneById(friend.getUserId())));
                    friendResponseList.add(friendResponse);
                }
        );
        return new PageImpl<>(friendResponseList, pageable, friendResponseList.size());
    }

    @Override
    public boolean isFriend(Long idSource, Long idTarget) {
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);
        if (userTarget==null || !userTarget.isActive()) throw new ObjectNotFoundException("Tài khoản không tồn tại");
        List<Friend> friendSourceList = userSource.getFriends();
        List<Friend> friendTargetList = userTarget.getFriends();
        for(Friend friend : friendSourceList)
            if(friend.getUser().getId() == userTarget.getId())
                return true;
        for(Friend friend : friendTargetList)
            if(friend.getUser().getId() == userSource.getId())
                return true;
        return false;
    }

    @Override
    public boolean itIsMe(Long idSource, Long idTarget) {
        return idSource == idTarget;
    }

}
