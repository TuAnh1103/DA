package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post/")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final Profile profile;
    @PostMapping("/add")
    public ResponseEntity<?> savePost(@Valid @RequestBody PostSaveRequest postSaveRequest){
        return postService.save(postSaveRequest);
    }

    @PutMapping("/{id}") // update post
    public ResponseEntity<?> updatePost(@PathVariable("id") Long idPost,@RequestBody PostSaveRequest postSaveRequest){
        return postService.update(idPost,postSaveRequest);
    }

    @DeleteMapping("/{id}") // delete post
    public ResponseEntity<?> deletePost(@PathVariable("id") Long idPost){
        return postService.delete(idPost);
    }

    @GetMapping("/getall/{id}")
    public ResponseEntity<List<PostResponse>> getAll(@PathVariable("id") Long id){
        return new ResponseEntity<>(postService.listPost(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getall/me")
    public ResponseEntity<List<PostResponse>> getAll(){
        return new ResponseEntity<>(postService.listPost(profile.getId()), HttpStatus.ACCEPTED);
    }
}
