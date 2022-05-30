package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.friendrequest.FriendRequestResponse;
import com.viuniteam.socialviuni.entity.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendRequestService {
    void save(FriendRequest friendRequest);

    void addFriendRequest(Long idTarget);

    void removeFriendRequest(Long idTarget);

    boolean isFriendRequest(Long idSource,Long idTarget);

    List<FriendRequestResponse> getAll();

    Page<FriendRequestResponse> getAllByUser(Pageable pageable);
}
