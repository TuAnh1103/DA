package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.service.ShareService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/share/")
public class ShareController {
    private final ShareService shareService;

    @PostMapping("/{postId}")
    public void sharePost(@PathVariable("postId") Long postId){
        shareService.share(postId);
    }
}
