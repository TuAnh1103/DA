package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.response.notification.NotificationPostResponse;
import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.entity.NotificationFollow;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.response.notification.NotificationResponseMapper;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationResponseMapper notificationResponseMapper;

    @Override
    public List<NotificationResponse> getAll(User user) {
        List<Notification> notificationList = notificationRepository.findAllByUser(user);
        List<NotificationResponse> notificationResponseList = new ArrayList<>();
        notificationList.stream().forEach(notification -> {
            NotificationResponse notificationResponse = notificationResponseMapper.from(notification);

            //set notification post
            NotificationPostResponse notificationPostResponse = NotificationPostResponse.builder()
                    .postId(notification.getNotificationPost().getId())
                    .notificationPostType(notification.getNotificationPost().getNotificationPostType())
                    .build();
            notificationResponse.setNotificationPostResponse(notificationPostResponse);


            notificationResponseList.add(notificationResponse);
        });
        return notificationResponseList;
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void createNotification(User user, String content, NotificationPost notificationPost) {
        Notification notification = Notification.builder()
                .notificationPost(notificationPost)
                .user(user)
                .content(content)
                .status(false)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(User user, String content, NotificationFollow notificationFollow) {
        Notification notification = Notification.builder()
                .notificationFollow(notificationFollow)
                .user(user)
                .content(content)
                .status(false)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void updateNotification(String content,NotificationPost notificationPost, boolean status) {
        Notification oldNotification = notificationRepository.findOneByNotificationPost(notificationPost);
        Notification newNotification = Notification.builder()
                .notificationPost(notificationPost)
                .user(oldNotification.getUser())
                .content(content)
                .status(status)
                .build();
        newNotification.setId(oldNotification.getId());
        notificationRepository.save(newNotification);
    }

    /*public void createNotification(NotificationSaveRequest notificationSaveRequest) {
//        NotificationPost notificationPost = NotificationPost.builder()
//                .post(postRepository.findOneById(notificationSaveRequest.getNotificationPostSaveRequests().get(0).getPostId()))
//                .notificationPostType(notificationSaveRequest.getNotificationPostSaveRequests().get(0).getNotificationPostType())
//                .build();
        NotificationPost notificationPost = NotificationPost.builder()
                .post(postRepository.findOneById(57L))
                .notificationPostType(NotificationPostType.COMMENT)
                .build();

        Follower follower = followerRepository.findOneById(17L);
        System.out.println(follower.getId());
        NotificationFollow notificationFollow = NotificationFollow.builder()
                .follower(follower)
                .build();

//        List<NotificationPost> notificationPostList = new ArrayList<>();
//        notificationPostList.add(notificationPostRepository.save(notificationPost));
        Notification notification = Notification.builder()
//                .notificationPost(notificationPost)
                .notificationFollow(notificationFollow)
                .user(userService.findOneById(notificationSaveRequest.getUserId()))
                .content("hello follower")
                .status(true)
                .build();
        notificationRepository.save(notification);
    }*/


}
