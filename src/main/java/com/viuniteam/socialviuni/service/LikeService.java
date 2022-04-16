package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    void like(Long postId);
    boolean checkLiked(Post post, User user);
    boolean checkDisliked(Post post,User user);
}
