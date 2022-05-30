package com.viuniteam.socialviuni.repository.notification;

import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Notification findOneByNotificationPost(NotificationPost notificationPost);
    Notification findOneById(Long id);
    Notification findOneByIdAndUser(Long id, User user);
    List<Notification> findAllByUserAndStatus(User user, NotificationSeenType status);
    Page<Notification> findAllByUserOrderByIdDesc(User user, Pageable pageable);
    void deleteById(Long id);
}
