package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.service.ShareService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/share/")
public class ShareController {
    private final ShareService shareService;

    @PostMapping("/{postId}")
    public ShareResponse sharePost(@Valid @RequestBody ShareSaveRequest shareSaveRequest, @PathVariable("postId") Long postId){
        return shareService.share(shareSaveRequest,postId);
    }
    @DeleteMapping("/{postId}")
    public void removeSharePost(@PathVariable("postId") Long shareId){
        shareService.removeShare(shareId);
    }
}
