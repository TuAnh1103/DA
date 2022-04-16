package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.entity.NotificationFollow;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getAll(User user);
    void delete(Long id);
    void seenNotification();
    void readNotification(Long id);
    void createNotification(User user, String content, NotificationPost notificationPost);
    void createNotification(User user, String content, NotificationFollow notificationFollow);
    void updateNotification(String content,NotificationPost notificationPost, NotificationSeenType status);
}
