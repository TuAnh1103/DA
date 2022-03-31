package com.viuniteam.socialviuni.service;


import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import org.springframework.http.ResponseEntity;

public interface ShareService {
    ShareResponse share(ShareSaveRequest shareSaveRequest, Long postId);
    void removeShare(Long shareId);
    Long countSharePost(Long postId);
}
