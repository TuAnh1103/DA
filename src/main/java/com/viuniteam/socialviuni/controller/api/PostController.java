package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final Profile profile;
    @PostMapping
    public PostResponse savePost(@Valid @RequestBody PostSaveRequest postSaveRequest){
        return postService.save(postSaveRequest);
    }
    @PutMapping("/{id}") // update post
    public PostResponse updatePost(@PathVariable("id") Long idPost,@RequestBody PostSaveRequest postSaveRequest){
        return postService.update(idPost,postSaveRequest);
    }

    @DeleteMapping("/{id}") // delete post
    public void deletePost(@PathVariable("id") Long idPost){
        postService.delete(idPost);
    }

    @GetMapping("/{postId}")
    public PostResponse findOneById(@PathVariable("postId") Long postId){
        return postService.findOneById(postId);
    }

    @GetMapping("/all/{userId}")
    public Page<PostResponse> getAllPage(@PathVariable("userId") Long userId, @RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return postService.listPost(userId,pageRequest);
    }

    @GetMapping("/all/me")
    public Page<PostResponse> getAll(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return postService.listPost(profile.getId(),pageRequest);
    }

}
