package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
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
    public ResponseEntity<?> sharePost(@Valid @RequestBody ShareSaveRequest shareSaveRequest, @PathVariable("postId") Long postId){
        return shareService.share(shareSaveRequest,postId);
    }
}
