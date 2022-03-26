package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.entity.Friend;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendService {
    void save(Friend friend);

    ResponseEntity<?> addFriend(Long idSource, Long idTarget);

    ResponseEntity<?> removeFriend(Long idSource, Long idTarget);

    ResponseEntity<?> getAll(Long id);

    boolean isFriend(Long idSource, Long idTarget);

    boolean itIsMe(Long idSource,Long idTarget);
}
