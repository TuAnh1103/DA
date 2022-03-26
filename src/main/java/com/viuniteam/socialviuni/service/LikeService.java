package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;

public interface LikeService {
    void like(Long postId);
    void removeLike(Long postId);
    boolean checkLiked(Post post, User user);
    Long countLikePost(Long postId);
}
