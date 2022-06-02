package com.viuniteam.socialviuni.repository.notification;

import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.entity.NotificationPost;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    @Modifying
    @Query(value = "delete n from notification_post np join notification n on np.id = n.notification_post_id and np.post_id = ?1", nativeQuery = true)
    void deleteNotificationByNotificationPostAndPostId(Long postId);

    @Modifying
    @Query(value = "delete n from notification_follow np join notification n on np.id = n.notification_follow_id and np.post_id = ?1", nativeQuery = true)
    void deleteNotificationByNotificationFollowAndPostId(Long postId);


    @Modifying
    @Query(value = "delete from notification_post where post_id = ?1", nativeQuery = true)
    void deleteNotificationPostByPostId(Long postId);

    @Modifying
    @Query(value = "delete from notification_follow where post_id = ?1", nativeQuery = true)
    void deleteNotificationFollowByPostId(Long postId);

}
