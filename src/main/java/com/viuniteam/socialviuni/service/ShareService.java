package com.viuniteam.socialviuni.service;


import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import org.springframework.http.ResponseEntity;

public interface ShareService {
    ResponseEntity<?> share(ShareSaveRequest shareSaveRequest, Long postId);
    void removeShare(Long postId);
    Long countSharePost(Long postId);
}
