package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.repository.LikeRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationPostRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.LikeService;
import com.viuniteam.socialviuni.service.NotificationService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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
        if(!checkLiked(post,user)){
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);

            //create notification

            if(notificationRepository.findOneByNotificationPost(
                    notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.LIKE)
            )==null){
                NotificationPost notificationPost = NotificationPost.builder()
                        .notificationPostType(NotificationPostType.LIKE)
                        .post(post)
                        .build();
                notificationService.createNotification(
                        post.getAuthor(),
                        user.getLastName()+" "+user.getFirstName()+" đã thích bài viết "+post.getContent().substring(0,30)+"...",
                        notificationPost);
            }
            else{
                List<Like> listLike = likeRepository.findAllByPostOrderByCreatedDate(post);
                Like lastLike = ListUtils.getLast(listLike);
                User userLastLike = lastLike.getUser();
                NotificationPost notificationPost = notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.LIKE);
                notificationService.updateNotification(
                        userLastLike.getLastName()+" "+userLastLike.getFirstName()+" và "+(likeRepository.countByPost(post)-1)+" người khác đã thích bài viết: "+post.getContent().substring(0,30)+"...",
                        notificationPost
                );
            }

            throw new OKException("Đã like");
        }
        likeRepository.deleteByUserAndPost(user,post);
        List<Like> listLike = likeRepository.findAllByPostOrderByCreatedDate(post);
        Like lastLike = ListUtils.getLast(listLike);
        User userLastLike = lastLike.getUser();
        NotificationPost notificationPost = notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.LIKE);
        notificationService.updateNotification(
                userLastLike.getLastName()+" "+userLastLike.getFirstName()+" và "+(likeRepository.countByPost(post)-1)+" người khác đã thích bài viết: "+post.getContent().substring(0,30)+"...",
                notificationPost
        );
        throw new OKException("Đã hủy like");
    }

    @Override
    public boolean checkLiked(Post post, User user) {
        return likeRepository.findOneByPostAndUser(post,user)!=null;
    }
}
