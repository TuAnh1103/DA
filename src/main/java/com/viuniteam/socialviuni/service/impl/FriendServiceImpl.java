package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.mapper.response.friend.FriendResponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserInfoResponseMapper;
import com.viuniteam.socialviuni.repository.FriendRepository;
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

public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;
    private final FriendResponseMapper friendResponseMapper;
    private final UserInfoResponseMapper userInfoResponseMapper;

    @Override
    public void save(Friend friend) {
        friendRepository.save(friend);
    }

    @Override
    public ResponseEntity<?> addFriend(Long idSource, Long idTarget) {

        if(this.itIsMe(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400, "Không thể kết bạn với chính mình"), HttpStatus.BAD_REQUEST);

        if(this.isFriend(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400, "Đã kết bạn rồi"), HttpStatus.BAD_REQUEST);
        else {
            User userSource = userService.findOneById(idSource);
            User userTarget = userService.findOneById(idTarget);
            List<Friend> friendSourceList = userSource.getFriends();
            List<Friend> friendTargetList = userTarget.getFriends();

            // add friend target to list
            Friend friendSource = new Friend();
            friendSource.setUser(userTarget);
            this.save(friendSource);
            friendSourceList.add(friendSource);
            userSource.setFriends(friendSourceList);
            userService.update(userSource);

            // add friend source to list
            Friend friendTarget = new Friend();
            friendTarget.setUser(userSource);
            this.save(friendTarget);
            friendTargetList.add(friendTarget);
            userTarget.setFriends(friendTargetList);
            userService.update(userTarget);

            return new ResponseEntity<>(new JsonException(200,"Kết bạn thành công"), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> removeFriend(Long idSource, Long idTarget) {

        if(this.itIsMe(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400, "Không thể hủy kết bạn với chính mình"), HttpStatus.BAD_REQUEST);
        if(this.isFriend(idSource,idTarget)){
            /*User userSource = userService.findOneById(idSource);
            User userTarget = userService.findOneById(idTarget);
            List<Friend> friendSourceList = userSource.getFriends();
            List<Friend> friendTargetList = userTarget.getFriends();
            Long idFriendSource=0L;
            Long idFriendTarget=0L;
            for(Friend friend : friendSourceList){
                if(friend.getUser().getId() == userTarget.getId()) {
                    idFriendSource= friend.getId();
                    break;
                }
            }
            for(Friend friend : friendTargetList){
                if(friend.getUser().getId() == userSource.getId()){
                    idFriendTarget = friend.getId();
                    break;
                }
            }
            friendRepository.deleteUserFriends(idFriendSource);
            friendRepository.deleteFriendById(idFriendSource);
            friendRepository.deleteUserFriends(idFriendTarget);
            friendRepository.deleteFriendById(idFriendTarget);*/

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

            return new ResponseEntity<>(new JsonException(200, "Hủy kết bạn thành công"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new JsonException(400,"Chưa kết bạn"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAll(Long id) {
        User user = userService.findOneById(id);
        if(user == null)
            return new ResponseEntity<>(new JsonException(404,"Người dùng không tồn tại"),HttpStatus.NOT_FOUND);

        List<Friend> friendList = user.getFriends();
        List<FriendResponse> friendResponseList = new ArrayList<>();
        friendList.forEach(friend -> {
            FriendResponse friendResponse = friendResponseMapper.from(friend);
            friendResponse.setUserInfoResponse(userInfoResponseMapper.from(friend.getUser()));
            friendResponseList.add(friendResponse);
        });
        return new ResponseEntity<>(friendResponseList,HttpStatus.ACCEPTED);
    }

    @Override
    public boolean isFriend(Long idSource, Long idTarget) {
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);
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
