package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.post.PostFilterRequest;
import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponse save(PostSaveRequest postSaveRequest);
    PostResponse update(Long id,PostSaveRequest postSaveRequest);
    void delete(Long id);
    Page<PostResponse> findAllByUserId(Long userId, Pageable pageable);

    Page<PostResponse> search(PostFilterRequest postFilterRequest);

    PostResponse findOneById(Long id);
    void autoCreatePost(String content, List<Image> images);
    boolean myPost(Long postId);
    boolean checkPrivacy(Post post, Profile profile);
    boolean checkPrivacy(Post post, Long userId);
    PostResponse newPost(Long userId);
}
