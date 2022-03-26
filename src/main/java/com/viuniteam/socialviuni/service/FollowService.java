package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.entity.Following;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowService {
    ResponseEntity<?> addFollow(Long idTarget);
    ResponseEntity<?> removeFollow(Long idTarget);

    ResponseEntity<?> getAllFollower(Long id);
    ResponseEntity<?> getAllFollowing(Long id);

    boolean isFollower(Long idSource,Long idTarget);
    boolean isFollowing(Long idSource,Long idTarget);

}
