package com.viuniteam.socialviuni.repository.notification;

import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPostRepository extends JpaRepository<NotificationPost,Long> {
    NotificationPost findOneById(Long id);
    NotificationPost findOneByPostAndNotificationPostType(Post post, NotificationPostType notificationPostType);
}
