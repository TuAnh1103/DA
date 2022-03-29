package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<?> like(Long postId);
    void removeLike(Long postId);
    boolean checkLiked(Post post, User user);
    Long countLikePost(Long postId);
}
