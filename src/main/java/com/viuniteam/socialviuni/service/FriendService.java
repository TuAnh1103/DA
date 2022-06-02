package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendService {
    List<UserInfoResponse> listFriendSuggestions(Long userId);

    void addFriend(Long idSource, Long idTarget);

    void removeFriend(Long idSource, Long idTarget);

    List<FriendResponse> getAll(Long id);

    Page<FriendResponse> getAllByUserId(Long userId, Pageable pageable);

    boolean isFriend(Long idSource, Long idTarget);

    boolean itIsMe(Long idSource,Long idTarget);
}
