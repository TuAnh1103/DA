package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.entity.NotificationFollow;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;

public interface NotificationService {
    void createNotification(User user, String content, NotificationPost notificationPost);
    void createNotification(User user, String content, NotificationFollow notificationFollow);
    void updateNotification(String content,NotificationPost notificationPost);
}
