package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.follow.FollowResponse;
import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.entity.Following;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowService {
    void addFollow(Long idTarget);
    void removeFollow(Long idTarget);

    List<FollowResponse> getAllFollower(Long id);
    List<FollowResponse> getAllFollowing(Long id);

    boolean isFollower(Long idSource,Long idTarget);
    boolean isFollowing(Long idSource,Long idTarget);

}
