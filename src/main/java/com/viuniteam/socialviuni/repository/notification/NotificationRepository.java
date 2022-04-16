package com.viuniteam.socialviuni.repository.notification;

import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Notification findOneByNotificationPost(NotificationPost notificationPost);
    Notification findOneById(Long id);
    Notification findOneByIdAndUser(Long id, User user);
    List<Notification> findAllByUserAndStatus(User user, NotificationSeenType status);
    List<Notification> findAllByUser(User user);
    void deleteById(Long id);
}
