package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendService {

    void addFriend(Long idSource, Long idTarget);

    void removeFriend(Long idSource, Long idTarget);

    List<FriendResponse> getAll(Long id);

    Page<FriendResponse> getAllByUserId(Long userId, Pageable pageable);

    boolean isFriend(Long idSource, Long idTarget);

    boolean itIsMe(Long idSource,Long idTarget);
}
