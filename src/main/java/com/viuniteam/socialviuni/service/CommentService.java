package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    CommentResponse save(CommentSaveRequest commentSaveRequest, Long postId);
    CommentResponse update(CommentUpdateRequest commentUpdateRequest, Long postId);
    void delete(Long postId, Long commentId);
    List<CommentResponse> findAllByPost(Long postId);
}
