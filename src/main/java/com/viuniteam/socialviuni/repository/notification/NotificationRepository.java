package com.viuniteam.socialviuni.repository.notification;

import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Notification findOneByNotificationPost(NotificationPost notificationPost);
    List<Notification> findAllByUser(User user);
    void deleteById(Long id);
}
