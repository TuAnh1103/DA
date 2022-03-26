package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.dto.response.friendrequest.FriendRequestResponse;
import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.FriendRequest;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.mapper.response.friend.FriendResponseMapper;
import com.viuniteam.socialviuni.mapper.response.friendrequest.FriendRequestResponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserInfoResponseMapper;
import com.viuniteam.socialviuni.repository.FriendRequestRepository;
import com.viuniteam.socialviuni.service.FriendRequestService;
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
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final FriendService friendService;
    private final Profile profile;
    private final FriendRequestResponseMapper friendRequestResponseMapper;
    private final UserInfoResponseMapper userInfoResponseMapper;
    @Override
    public void save(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public ResponseEntity<?> addFriendRequest(Long idTarget) {
        Long idSource = profile.getId();
        if(friendService.isFriend(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400, "Đã kết bạn rồi"), HttpStatus.BAD_REQUEST);
        else {
            if (this.isFriendRequest(idSource,idTarget))
                return new ResponseEntity<>(new JsonException(400, "Đã gửi lời mời kết bạn rồi"), HttpStatus.BAD_REQUEST);
            if (friendService.itIsMe(profile.getId(),idTarget))
                return new ResponseEntity<>(new JsonException(400, "Không thể gửi lời mời kết bạn với chính mình"), HttpStatus.BAD_REQUEST);
            else {
                User userSource = userService.findOneById(idSource);
                User userTarget = userService.findOneById(idTarget);
                if(this.isFriendRequest(idTarget,idSource)){ // neu user1 add user2 roi nhung user2 tiep tuc add user 1 thi cho ca 2 thanh ban luon
                    List<FriendRequest> friendRequestSourceList = userSource.getFriendRequests();
                    for(FriendRequest friendRequest : friendRequestSourceList){
                        if(friendRequest.getUser().getId() == userTarget.getId()){
                            friendRequestSourceList.remove(friendRequest);
                            userSource.setFriendRequests(friendRequestSourceList);
                            userService.update(userSource);
                            friendRequestRepository.deleteFriendRequestById(friendRequest.getId());
                            break;
                        }
                    }
                    friendService.addFriend(idSource,idTarget);
                    return new ResponseEntity<>(new JsonException(200, "Chấp nhận lời mời kết bạn thành công"), HttpStatus.CREATED);
                }
                else {
                    List<FriendRequest> friendRequestTargetList = userTarget.getFriendRequests();
                    // add friend request source to list target
                    FriendRequest friendTarget = new FriendRequest();
                    friendTarget.setUser(userSource);
                    this.save(friendTarget);
                    friendRequestTargetList.add(friendTarget);
                    userTarget.setFriendRequests(friendRequestTargetList);
                    userService.update(userTarget);
                    return new ResponseEntity<>(new JsonException(200, "Đã gửi lời mời kết bạn thành công"), HttpStatus.CREATED);
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> removeFriendRequest(Long idTarget) {
        Long idSource = profile.getId();
        if(friendService.isFriend(idSource,idTarget))
            return new ResponseEntity<>(new JsonException(400, "Đã kết bạn rồi"), HttpStatus.BAD_REQUEST);
        else {
            if (this.isFriendRequest(idSource,idTarget) || this.isFriendRequest(idTarget,idSource)){//kiem tra xem 1 trong 2 nguoi co gui loi moi ket ban voi nhau khong

                /*User userSource = userService.findOneById(idSource);
                User userTarget = userService.findOneById(idTarget);
                List<FriendRequest> friendRequestTargetList = userTarget.getFriendRequests();

                Long idFriendSource=0L;
                for(FriendRequest friendRequest : friendRequestTargetList){
                    if(friendRequest.getUser().getId() == userSource.getId()) {
                        idFriendSource = friendRequest.getId();
                        break;
                    }
                }
                // remove friend request source from list target
                friendRequestRepository.deleteUserFriendRequests(idFriendSource);
                friendRequestRepository.deleteFriendRequestById(idFriendSource);*/

                User userSource = userService.findOneById(idSource);
                User userTarget = userService.findOneById(idTarget);
                List<FriendRequest> friendRequestSourceList = userSource.getFriendRequests();
                List<FriendRequest> friendRequestTargetList = userTarget.getFriendRequests();

                for(FriendRequest friendRequest : friendRequestSourceList){
                    if(friendRequest.getUser().getId() == userTarget.getId()) {
                        friendRequestSourceList.remove(friendRequest);
                        userSource.setFriendRequests(friendRequestSourceList);
                        userService.update(userSource);
                        friendRequestRepository.deleteFriendRequestById(friendRequest.getId());
                        break;
                    }
                }
                for(FriendRequest friendRequest : friendRequestTargetList){
                    if(friendRequest.getUser().getId() == userSource.getId()) {
                        friendRequestTargetList.remove(friendRequest);
                        userTarget.setFriendRequests(friendRequestTargetList);
                        userService.update(userTarget);
                        friendRequestRepository.deleteFriendRequestById(friendRequest.getId());
                        break;
                    }
                }
                return new ResponseEntity<>(new JsonException(200, "Đã hủy lời mời kết bạn"), HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<>(new JsonException(400, "Chưa gửi lời mời kết bạn"), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public boolean isFriendRequest(Long idSource, Long idTarget){
        User userSource = userService.findOneById(idSource);
        User userTarget = userService.findOneById(idTarget);
        List<FriendRequest> friendRequestTargetList = userTarget.getFriendRequests();
        for(FriendRequest friendRequest : friendRequestTargetList){
            if(friendRequest.getUser().getId() == userSource.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<?> getAll() {
        User user = userService.findOneById(profile.getId());
        if(user == null)
            return new ResponseEntity<>(new JsonException(404,"Người dùng không tồn tại"),HttpStatus.NOT_FOUND);

        List<FriendRequest> friendRequestList = user.getFriendRequests();
        List<FriendRequestResponse> friendRequestResponseList = new ArrayList<>();
        friendRequestList.forEach(friend -> {
            FriendRequestResponse friendRequestResponse = friendRequestResponseMapper.from(friend);
            friendRequestResponse.setUserInfoResponse(userInfoResponseMapper.from(friend.getUser()));
            friendRequestResponseList.add(friendRequestResponse);
        });
        return new ResponseEntity<>(friendRequestResponseList,HttpStatus.ACCEPTED);
    }
}
