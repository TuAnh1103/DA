package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.follow.FollowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowService {
    void addFollow(Long idTarget);
    void removeFollow(Long idTarget);

    List<FollowResponse> getAllFollower(Long id);
    List<FollowResponse> getAllFollowing(Long id);

    Page<FollowResponse> getAllFollowerByUserId(Long id, Pageable pageable);
    Page<FollowResponse> getAllFollowingByUserId(Long id, Pageable pageable);

    boolean isFollower(Long idSource,Long idTarget);
    boolean isFollowing(Long idSource,Long idTarget);

}
