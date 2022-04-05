package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}")
    public void like(@PathVariable("postId") Long postId){
        likeService.like(postId);
    }

//    @GetMapping("/count/{postId}")
//    public Long countLike(@PathVariable("postId") Long postId){
//        return likeService.countLikePost(postId);
//    }

}
