package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.entity.Image;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    ResponseEntity<?> save(PostSaveRequest postSaveRequest);
    ResponseEntity<?> update(Long id,PostSaveRequest postSaveRequest);
    ResponseEntity<?> delete(Long id);
    List<PostResponse> listPost(Long id);
    PostResponse findOneById(Long id);
    void autoCreatePost(String content, List<Image> images);
    boolean myPost(Long id);
}
