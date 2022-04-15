package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.entity.NotificationFollow;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getAll();
    void createNotification(User user, String content, NotificationPost notificationPost);
    void createNotification(User user, String content, NotificationFollow notificationFollow);
    void updateNotification(String content,NotificationPost notificationPost);
}
