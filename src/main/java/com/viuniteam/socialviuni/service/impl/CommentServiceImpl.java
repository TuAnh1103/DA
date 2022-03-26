package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.mapper.request.comment.CommentRequestMapper;
import com.viuniteam.socialviuni.mapper.request.comment.CommentUpdateRequestMapper;
import com.viuniteam.socialviuni.mapper.response.comment.CommentResponseMapper;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.CommentService;
import com.viuniteam.socialviuni.service.ImageService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentRequestMapper commentRequestMapper;
    private final CommentUpdateRequestMapper commentUpdateRequestMapper;
    private final CommentResponseMapper commentResponseMapper;
    private final UserAuthorResponseUtils userAuthorResponseUtils;
    private final ImageReponseMapper imageReponseMapper;
    private final ImageService imageService;
    private final UserService userService;
    private final Profile profile;
    @Override
    public ResponseEntity<?> save(CommentSaveRequest commentSaveRequest, Long postId) {
        Comment comment = commentRequestMapper.to(commentSaveRequest);
        comment.setImages(ListUtils.onetoList(imageService.findOneById(commentSaveRequest.getImageId())));
        Post post = postRepository.findOneById(postId);
        return convertToCommentResponse(post,comment);
    }

    @Override
    public ResponseEntity<?> update(CommentUpdateRequest commentUpdateRequest, Long postId) {
        Comment comment = commentUpdateRequestMapper.to(commentUpdateRequest);
        comment.setImages(ListUtils.onetoList(imageService.findOneById(commentUpdateRequest.getImageId())));
        Post post = postRepository.findOneById(postId);

        for(Comment cmt : post.getComments()){
            if(cmt.getId().equals(comment.getId())){
                if(cmt.getUser().getId().equals(profile.getId())){
                    comment.setCreatedDate(cmt.getCreatedDate());
                    return convertToCommentResponse(post,comment);
                }
                return new ResponseEntity<>(new JsonException(400,"không có quyền sửa comment"), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(new JsonException(404,"Comment không tồn tại"), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> convertToCommentResponse(Post post, Comment comment){
        if(post!=null){
            if(post.getAuthor().isActive()){
                User author = userService.findOneById(profile.getId());
                comment.setPost(post);
                comment.setUser(author);
                Comment commentSuccess = commentRepository.save(comment);

                CommentResponse commentResponse= commentResponseMapper.from(commentSuccess);
                commentResponse.setUserAuthorResponse(userAuthorResponseUtils.convert(comment.getUser()));
                commentResponse.setImageResponse(imageReponseMapper.from(ListUtils.getLast(comment.getImages())));

                return new ResponseEntity<>(commentResponse,HttpStatus.CREATED);
            }

            return new ResponseEntity<>(new JsonException(404,"Bài viêt không tồn tại"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new JsonException(404,"Bài viêt không tồn tại"), HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long postId, Long commentId) {
        Post post = postRepository.findOneById(postId);
        if(post!=null){
            if(post.getAuthor().isActive()){
                Comment comment = commentRepository.findOneById(commentId);
                if(comment!=null){
                    for(Comment cmt : post.getComments()){
                        if(cmt.getId().equals(comment.getId())){
                            if(cmt.getUser().getId().equals(profile.getId()))
                                commentRepository.deleteById(commentId);
                            break;
                        }
                    }
                }

            }
        }
        //commentRepository.deleteComment(postId,profile.getId(),commentId);
    }

    @Override
    public ResponseEntity<?> findAllByPost(Long postId) {
        Post post = postRepository.findOneById(postId);
        if (post!=null){
            List<Comment> comments = post.getComments();
            List<CommentResponse> commentResponses = new ArrayList<>();
            for(Comment comment: comments){
                    CommentResponse commentResponse = commentResponseMapper.from(comment);
                    commentResponse.setUserAuthorResponse(userAuthorResponseUtils.convert(comment.getUser()));
                    commentResponse.setImageResponse(imageReponseMapper.from(ListUtils.getLast(comment.getImages())));
                    commentResponses.add(commentResponse);
            }
            return new ResponseEntity<>(commentResponses,HttpStatus.CREATED);
        }
        return null;
    }

}
