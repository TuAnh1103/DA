package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.annotation.HandlingOffensive;
import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.dto.utils.comment.CommentResponseUltils;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.request.comment.CommentRequestMapper;
import com.viuniteam.socialviuni.mapper.request.comment.CommentUpdateRequestMapper;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.*;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentRequestMapper commentRequestMapper;
    private final CommentUpdateRequestMapper commentUpdateRequestMapper;
    private final ImageService imageService;
    private final UserService userService;
    private final Profile profile;
    private final CommentResponseUltils commentResponseUltils;
    private final HandlingOffensive handlingOffensive;
    @Override
    public CommentResponse save(CommentSaveRequest commentSaveRequest, Long postId) {
        //check noi dung comment tu ngu tho tuc
        handlingOffensive.handling(commentSaveRequest);
        Comment comment = commentRequestMapper.to(commentSaveRequest);
        comment.setUser(userService.findOneById(profile.getId()));
        comment.setImages(ListUtils.oneToList(imageService.findOneById(commentSaveRequest.getImageId())));
        Post post = postRepository.findOneById(postId);
        return convertToCommentResponse(post,comment);
    }

    @Override
    public CommentResponse update(CommentUpdateRequest commentUpdateRequest) {
        /*if(offensiveKeywordService.isExist(commentUpdateRequest.getContent()))
            throw new BadRequestException("Comment không được chứa từ ngữ thô tục");
        Comment comment = commentUpdateRequestMapper.to(commentUpdateRequest);
        comment.setImages(ListUtils.oneToList(imageService.findOneById(commentUpdateRequest.getImageId())));
        Post post = postRepository.findOneById(postId);
        if(post == null) throw new ObjectNotFoundException("Bài viết không tồn tại");
        Comment commentFilter = post.getComments().stream()
                .filter(cmt-> cmt.getId().equals(comment.getId()))
                .findAny()
                .orElse(null);
        if(commentFilter==null) throw new ObjectNotFoundException("Comment không tồn tại");
        if(commentFilter.getUser().getId().equals(profile.getId()) ||  userService.isAdmin(profile)){
            comment.setCreatedDate(commentFilter.getCreatedDate());
            return convertToCommentResponse(post,comment);
        }
        throw new BadRequestException("Không có quyền sửa comment");*/

        Comment oldComment = commentRepository.findOneById(commentUpdateRequest.getId());
        if(oldComment == null)
            throw new ObjectNotFoundException("Comment không tồn tại");

        if(!oldComment.getPost().getAuthor().isActive() && !userService.isAdmin(profile))
            throw new ObjectNotFoundException("Bài viết không tồn tại");

        //check noi dung comment tu ngu tho tuc
        handlingOffensive.handling(commentUpdateRequest);

        Comment newComment = commentUpdateRequestMapper.to(commentUpdateRequest);
        newComment.setImages(ListUtils.oneToList(imageService.findOneById(commentUpdateRequest.getImageId())));

        if(oldComment.getUser().getId().equals(profile.getId()) ||  userService.isAdmin(profile)){
            newComment.setCreatedDate(oldComment.getCreatedDate());
            newComment.setUser(oldComment.getUser());
            return convertToCommentResponse(oldComment.getPost(),newComment);
        }
        throw new BadRequestException("Không có quyền sửa comment");
    }

    private CommentResponse convertToCommentResponse(Post post, Comment comment){
        if(post!=null){
            if(post.getAuthor().isActive() || userService.isAdmin(profile)){
                comment.setPost(post);
                Comment commentSuccess = commentRepository.save(comment);
                return commentResponseUltils.convert(commentSuccess);
            }
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        }
        throw new ObjectNotFoundException("Bài viết không tồn tại");
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findOneById(commentId);
        if(comment == null)
            throw new ObjectNotFoundException("Comment không tồn tại");

        if(!comment.getPost().getAuthor().isActive() && !userService.isAdmin(profile))
            throw new ObjectNotFoundException("Bài viết không tồn tại");

        if(comment.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
            commentRepository.deleteById(commentId);
            throw new OKException("Xóa comment thành công");
        }
        throw new BadRequestException("Không có quyền xóa comment");
    }

    @Override
    public Page<CommentResponse> findAllByPost(Long postId, Pageable pageable) {
        Post post = postRepository.findOneById(postId);
        if(post==null || (!post.getAuthor().isActive()&& userService.isAdmin(profile)) || !postService.checkPrivicy(post,profile))
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        Page<Comment> commentPage = commentRepository.findAllByPostOrderByIdDesc(post,pageable);
        List<CommentResponse> commentResponses = commentPage
                .stream()
                .filter(comment -> comment.getUser().isActive() || userService.isAdmin(profile))
                .map(commentResponseUltils::convert)
                .collect(Collectors.toList());
        Collections.sort(commentResponses, new Comparator<CommentResponse>() { // sap xep lai comment theo thu tu tang dan cua id
            @Override
            public int compare(CommentResponse o1, CommentResponse o2) {
                if(o1.getId()>o2.getId()) return 1;
                else if(o1.getId()<o2.getId()) return -1;
                else return 1;
            }
        });
        return new PageImpl<>(commentResponses,pageable,commentResponses.size());
    }
}
