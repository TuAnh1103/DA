package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentSaveRequest commentSaveRequest, Long postId);
    CommentResponse update(CommentUpdateRequest commentUpdateRequest);
    void delete(Long commentId);
    Page<CommentResponse> findAllByPost(Long postId, Pageable pageable);
}
