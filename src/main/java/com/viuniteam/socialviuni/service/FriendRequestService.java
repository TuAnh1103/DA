package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.FriendRequest;
import org.springframework.http.ResponseEntity;

public interface FriendRequestService {
    void save(FriendRequest friendRequest);

    ResponseEntity<?> addFriendRequest( Long idTarget);

    ResponseEntity<?> removeFriendRequest(Long idTarget);

    boolean isFriendRequest(Long idSource,Long idTarget);

    ResponseEntity<?> getAll();
}
