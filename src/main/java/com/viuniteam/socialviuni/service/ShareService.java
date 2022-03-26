package com.viuniteam.socialviuni.service;


public interface ShareService {
    void share(Long postId);
    void removeShare(Long postId);
    Long countSharePost(Long postId);
}
