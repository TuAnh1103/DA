package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> save(CommentSaveRequest commentSaveRequest, Long postId);
    ResponseEntity<?> update(CommentUpdateRequest commentUpdateRequest, Long postId);
    void delete(Long postId, Long commentId);
    ResponseEntity<?> findAllByPost(Long postId);
}
