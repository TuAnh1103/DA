package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.dto.request.comment.CommentUpdateRequest;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.dto.utils.comment.CommentResponseUtils;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.request.comment.CommentRequestMapper;
import com.viuniteam.socialviuni.mapper.request.comment.CommentUpdateRequestMapper;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationPostRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.service.*;
import com.viuniteam.socialviuni.utils.ListUtils;
import com.viuniteam.socialviuni.utils.ShortContent;
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
    private final CommentResponseUtils commentResponseUtils;

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    protected final NotificationPostRepository notificationPostRepository;

//    private final HandlingOffensive handlingOffensive;
    @Override
    public CommentResponse save(CommentSaveRequest commentSaveRequest, Long postId) {
        //check noi dung comment tu ngu tho tuc
        //handlingOffensive.handling(commentSaveRequest);

        Comment comment = commentRequestMapper.to(commentSaveRequest);
        comment.setUser(userService.findOneById(profile.getId()));
        comment.setImages(ListUtils.oneToList(imageService.findOneById(commentSaveRequest.getImageId())));
        Post post = postRepository.findOneById(postId);

        return convertToCommentResponse(post,comment, "CREATE");
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
        //handlingOffensive.handling(commentUpdateRequest);

        Comment newComment = commentUpdateRequestMapper.to(commentUpdateRequest);
        newComment.setImages(ListUtils.oneToList(imageService.findOneById(commentUpdateRequest.getImageId())));

        if(oldComment.getUser().getId().equals(profile.getId()) ||  userService.isAdmin(profile)){
            newComment.setCreatedDate(oldComment.getCreatedDate());
            newComment.setUser(oldComment.getUser());
            return convertToCommentResponse(oldComment.getPost(),newComment, "UPDATE");
        }
        throw new BadRequestException("Không có quyền sửa comment");
    }

    private CommentResponse convertToCommentResponse(Post post, Comment comment, String typeComment){
        if(post!=null){
            if(post.getAuthor().isActive() || userService.isAdmin(profile)){
                comment.setPost(post);
                Comment commentSuccess = commentRepository.save(comment);
                if(typeComment.equals("CREATE")){
                    createNotification(post,comment.getUser(),NotificationSeenType.NOT_SEEN);
                }
                return commentResponseUtils.convert(commentSuccess);
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
            createNotification(comment.getPost(),comment.getUser(),NotificationSeenType.SEEN); // update notification
            throw new OKException("Xóa comment thành công");
        }
        throw new BadRequestException("Không có quyền xóa comment");
    }

    @Override
    public Page<CommentResponse> findAllByPost(Long postId, Pageable pageable) {
        Post post = postRepository.findOneById(postId);
        if(post==null || (!post.getAuthor().isActive()&& userService.isAdmin(profile)) || !postService.checkPrivacy(post,profile))
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        Page<Comment> commentPage = commentRepository.findAllByPostOrderByIdDesc(post,pageable);
        List<CommentResponse> commentResponses = commentPage
                .stream()
                .filter(comment -> comment.getUser().isActive() || userService.isAdmin(profile))
                .map(commentResponseUtils::convert)
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

    private void createNotificationComment(Post post, User user){
        NotificationPost notificationPost = NotificationPost.builder()
                .notificationPostType(NotificationPostType.COMMENT)
                .post(post)
                .build();
        notificationService.createNotification(
                post.getAuthor(),
                user.getLastName()+" "+user.getFirstName()+" đã bình luận bài viết: "
                        + ShortContent.convertToShortContent(post.getContent()),
                notificationPost);
    }
    private void updateNotificationComment(Post post, NotificationSeenType status){
        Long commentCount = commentRepository.countByPostGroupByUser(post.getId());
        if(commentCount > 0){
            User userNewComment = commentRepository.findTop1ByPostOrderByCreatedDateDesc(post).getUser();

            String fullName = userNewComment.getLastName()+" "+userNewComment.getFirstName();

            String postShortContent = ShortContent.convertToShortContent(post.getContent());
            String content;

            if(commentCount == 1)
                content = fullName+" đã bình luận bài viết: "+postShortContent;
            else
                content = fullName +" và "+(commentCount-1) +" người khác đã bình luận bài viết: "+postShortContent;

            NotificationPost notificationPost = notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.COMMENT);
            notificationService.updateNotification(content,notificationPost,status);
        }
    }

    private void createNotification(Post post, User user, NotificationSeenType status){
        if(notificationRepository.findOneByNotificationPost( // check if notification comment not exist then create notification
                notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.COMMENT))==null)
            createNotificationComment(post,user);
        else
            updateNotificationComment(post,status); // update notification
    }

}
