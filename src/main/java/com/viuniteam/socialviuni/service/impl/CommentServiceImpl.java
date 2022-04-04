package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.request.comment.CommentRequestMapper;
import com.viuniteam.socialviuni.mapper.request.comment.CommentUpdateRequestMapper;
import com.viuniteam.socialviuni.mapper.response.comment.CommentResponseMapper;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.CommentService;
import com.viuniteam.socialviuni.service.ImageService;
import com.viuniteam.socialviuni.service.OffensiveKeywordService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
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
    private final OffensiveKeywordService offensiveKeywordService;
    @Override
    public CommentResponse save(CommentSaveRequest commentSaveRequest, Long postId) {
        if(offensiveKeywordService.isExist(commentSaveRequest.getContent()))
            throw new BadRequestException("Comment không được chứa từ ngữ thô tục");
        Comment comment = commentRequestMapper.to(commentSaveRequest);
        comment.setImages(ListUtils.oneToList(imageService.findOneById(commentSaveRequest.getImageId())));
        Post post = postRepository.findOneById(postId);
        return convertToCommentResponse(post,comment);
    }

    @Override
    public CommentResponse update(CommentUpdateRequest commentUpdateRequest, Long postId) {
        if(offensiveKeywordService.isExist(commentUpdateRequest.getContent()))
            throw new BadRequestException("Comment không được chứa từ ngữ thô tục");
        Comment comment = commentUpdateRequestMapper.to(commentUpdateRequest);
        comment.setImages(ListUtils.oneToList(imageService.findOneById(commentUpdateRequest.getImageId())));
        Post post = postRepository.findOneById(postId);
        if(post == null) throw new ObjectNotFoundException("Bài viết không tồn tại");
        Comment commentFilter = post.getComments().stream().filter(cmt-> cmt.getId().equals(comment.getId())).findAny().orElse(null);
        if(commentFilter==null) throw new ObjectNotFoundException("Comment không tồn tại");
        if(commentFilter.getUser().getId().equals(profile.getId()) ||  userService.isAdmin(profile)){
            comment.setCreatedDate(commentFilter.getCreatedDate());
            return convertToCommentResponse(post,comment);
        }
        throw new BadRequestException("không có quyền sửa comment");
    }

    private CommentResponse convertToCommentResponse(Post post, Comment comment){
        if(post!=null){
            if(post.getAuthor().isActive()){
                User author = userService.findOneById(profile.getId());
                comment.setPost(post);
                comment.setUser(author);
                Comment commentSuccess = commentRepository.save(comment);

                CommentResponse commentResponse= commentResponseMapper.from(commentSuccess);
                commentResponse.setUserAuthorResponse(userAuthorResponseUtils.convert(comment.getUser()));
                commentResponse.setImageResponse(imageReponseMapper.from(ListUtils.getLast(comment.getImages())));

                return commentResponse;
            }

            throw new ObjectNotFoundException("Bài viết không tồn tại");
        }
        throw new ObjectNotFoundException("Bài viết không tồn tại");
    }

    @Override
    public void delete(Long postId, Long commentId) {
        Post post = postRepository.findOneById(postId);
        if(post == null || !post.getAuthor().isActive())
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        Comment comment = commentRepository.findOneById(commentId);
        if(comment == null)
            throw new ObjectNotFoundException("Comment không tồn tại");
        Comment commentFilter = post.getComments().stream().filter(cmt->cmt.getId().equals(comment.getId())).findAny().orElse(null);
        if(commentFilter==null)
            throw new ObjectNotFoundException("Comment không tồn tại");
        if(commentFilter.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
            commentRepository.deleteById(commentId);
            throw new OKException("Xóa comment thành công");
        }
        throw new BadRequestException("Không có quyền xóa comment");
    }

    @Override
    public List<CommentResponse> findAllByPost(Long postId) {
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
            return commentResponses;
        }
        return null;
    }

}
