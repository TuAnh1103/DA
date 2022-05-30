package com.viuniteam.socialviuni.service;


import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShareService {
    ShareResponse share(ShareSaveRequest shareSaveRequest, Long postId);
    void remove(Long shareId);
    Long countSharePost(Long postId);
    ShareResponse update(ShareSaveRequest shareSaveRequest, Long shareId);
    Page<ShareResponse> listShare(Long userId, Pageable pageable);
    ShareResponse newShare(Long userId);
}
