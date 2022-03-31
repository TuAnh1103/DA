package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comment/")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add/{postId}")
    public CommentResponse addComment(@RequestBody CommentSaveRequest commentSaveRequest, @PathVariable("postId") Long postId){
        return commentService.save(commentSaveRequest,postId);
    }

    @PutMapping("/update/{postId}")
    public CommentResponse updateComment(@RequestBody CommentUpdateRequest commentUpdateRequest, @PathVariable("postId") Long postId){
        return commentService.update(commentUpdateRequest,postId);
    }

    @DeleteMapping("/delete/{postId}/{commentId}")
    public void deleteComment(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId){
        commentService.delete(postId,commentId);
    }

    @GetMapping("/getall/{postId}")
    public List<CommentResponse> getAll(@PathVariable("postId") Long postId){
        return commentService.findAllByPost(postId);
    }
}
