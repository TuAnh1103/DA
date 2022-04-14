package com.viuniteam.socialviuni.repository.notification;

import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.entity.NotificationPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Notification findOneByNotificationPost(NotificationPost notificationPost);
}
