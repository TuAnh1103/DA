package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.repository.LikeRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationPostRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.LikeService;
import com.viuniteam.socialviuni.service.NotificationService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.ShortContent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final Profile profile;
    private final UserService userService;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    protected final NotificationPostRepository notificationPostRepository;
    @Override
    public void like(Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post == null || !post.getAuthor().isActive()) throw new ObjectNotFoundException("Bài viết không tồn tại");
        User user = userService.findOneById(profile.getId());

        // like
        if(!checkLiked(post,user)){
            if(checkDisliked(post,user)){ // if dislike then like again
                Like like = likeRepository.findOneByPostAndUser(post,user);
                like.setStatus(true);
                likeRepository.save(like);
                createNotification(post,user,NotificationSeenType.SEEN);
                throw new OKException("Đã like");
            }

            // if not liked and disliked then create notification like
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .status(true)
                    .build();
            likeRepository.save(like);

            // create notification like
            createNotification(post,user,NotificationSeenType.NOT_SEEN);
            throw new OKException("Đã like");
        }

        //dislike
        Like like = likeRepository.findOneByPostAndUser(post,user);
        like.setStatus(false);
        likeRepository.save(like);
        createNotification(post,user,NotificationSeenType.SEEN);
        throw new OKException("Đã hủy like");
    }

    private void createNotificationLike(Post post,User user){
        NotificationPost notificationPost = NotificationPost.builder()
                .notificationPostType(NotificationPostType.LIKE)
                .post(post)
                .build();

        notificationService.createNotification(
                post.getAuthor(),
                user.getLastName()+" "+user.getFirstName()+" đã thích bài viết: "+ ShortContent.convertToShortContent(post.getContent()),
                notificationPost);
    }

    private void updateNotificationLike(Post post, NotificationSeenType status){
        Long likeCount = likeRepository.countByPostAndStatus(post,true);
        if(likeCount > 0){
            User userNewLike = likeRepository.findTop1ByPostAndStatusOrderByCreatedDateDesc(post,true).getUser();

            String fullName=userNewLike.getLastName()+" "+userNewLike.getFirstName();

            String postShortContent = ShortContent.convertToShortContent(post.getContent());
            String content;

            if(likeCount == 1)
                content = fullName+" đã thích bài viết: "+postShortContent;
            else
                content = fullName +" và "+(likeCount-1) +" người khác đã thích bài viết: "+postShortContent;

            NotificationPost notificationPost = notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.LIKE);
            notificationService.updateNotification(content,notificationPost,status);
        }
    }

    private void createNotification(Post post, User user, NotificationSeenType status){
            if(notificationRepository.findOneByNotificationPost( // check if notification like not exist then create notification
                    notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.LIKE))==null)
                createNotificationLike(post,user);
            else
                updateNotificationLike(post,status); // update notification
    }

    @Override
    public boolean checkLiked(Post post, User user) {
        return likeRepository.existsByPostAndUserAndStatus(post,user, true);
    }

    @Override
    public boolean checkDisliked(Post post, User user) {
        return likeRepository.existsByPostAndUserAndStatus(post,user,false);
    }
}
