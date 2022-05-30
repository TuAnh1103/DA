package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.notification.NotificationFollowResponse;
import com.viuniteam.socialviuni.dto.response.notification.NotificationPostResponse;
import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.entity.*;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.response.notification.NotificationResponseMapper;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.service.NotificationService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationResponseMapper notificationResponseMapper;
    private final UserService userService;
    private final Profile profile;

    @Override
    public Page<NotificationResponse> getAll(User user, Pageable pageable) {
        Page<Notification> notificationPage = notificationRepository.findAllByUserOrderByIdDesc(user, pageable);
        List<NotificationResponse> notificationResponseList = new ArrayList<>();
        notificationPage.stream().forEach(notification -> {
            NotificationResponse notificationResponse = notificationResponseMapper.from(notification);

            NotificationPost notificationPost = notification.getNotificationPost();
            NotificationFollow notificationFollow = notification.getNotificationFollow();
            //set notification post
            if(notificationPost!=null){

                NotificationPostResponse notificationPostResponse = NotificationPostResponse.builder()
                        .postId(notificationPost.getId())
                        .notificationPostType(notificationPost.getNotificationPostType())
                        .build();

                // set avatar user like share comment
                if(notificationPost.getNotificationPostType().equals(NotificationPostType.LIKE)){
                    Image avatar =ListUtils.getLast(ListUtils.getLast(notificationPost.getPost().getLikes()).getUser().getAvatarImage());
                    if(avatar!=null)
                        notificationPostResponse.setAvatar(avatar.getLinkImage());
                }
                if(notificationPost.getNotificationPostType().equals(NotificationPostType.COMMENT)){
                    Image avatar =ListUtils.getLast(ListUtils.getLast(notificationPost.getPost().getComments()).getUser().getAvatarImage());
                    if(avatar!=null)
                        notificationPostResponse.setAvatar(avatar.getLinkImage());
                }
                if(notificationPost.getNotificationPostType().equals(NotificationPostType.SHARE)){
                    Image avatar =ListUtils.getLast(ListUtils.getLast(notificationPost.getPost().getShares()).getUser().getAvatarImage());
                    if(avatar!=null)
                        notificationPostResponse.setAvatar(avatar.getLinkImage());
                }

                notificationResponse.setNotificationPostResponse(notificationPostResponse);
            }

            //set notification follow
            if(notificationFollow!=null){
                NotificationFollowResponse notificationFollowResponse = new NotificationFollowResponse();
                Post postFollow = notificationFollow.getPost();
                Share shareFollow = notificationFollow.getShare();
                if(postFollow!=null){    // set post and avatar to follow response
                    notificationFollowResponse.setPostId(postFollow.getId());
                    Image avatar = ListUtils.getLast(postFollow.getAuthor().getAvatarImage());
                    if(avatar!=null)
                        notificationFollowResponse.setAvatar(avatar.getLinkImage());
                }
                if(shareFollow!=null){  // set share and avatar to follow response
                    notificationFollowResponse.setShareId(shareFollow.getId());
                    Image avatar = ListUtils.getLast(shareFollow.getUser().getAvatarImage());
                    if(avatar!=null)
                        notificationFollowResponse.setAvatar(avatar.getLinkImage());
                }

                notificationResponse.setNotificationFollowResponse(notificationFollowResponse);
            }
            notificationResponseList.add(notificationResponse);
        });
        return new PageImpl<>(notificationResponseList, pageable, notificationResponseList.size());
    }

    @Override
    public void delete(Long id) {
        Notification notification = notificationRepository.findOneById(id);
        if(notification==null)
            throw new ObjectNotFoundException("Thông báo không tồn tại");
        if(!notification.getUser().getId().equals(profile.getId()) && !userService.isAdmin(profile))
            throw new BadRequestException("Không có quyền xóa thông báo");
        notificationRepository.deleteById(id);
        throw new OKException("Xóa thông báo thành công");
    }

    @Override
    public void seenNotification() {
        List<Notification> notificationList = notificationRepository.findAllByUserAndStatus(userService.findOneById(profile.getId()),NotificationSeenType.NOT_SEEN);
        notificationList.stream().forEach(notification->{
            notification.setStatus(NotificationSeenType.SEEN);
            notificationRepository.save(notification);
        });
    }

    @Override
    public void readNotification(Long id) {
        Notification notification = notificationRepository.findOneByIdAndUser(id,userService.findOneById(profile.getId()));
        if(notification!=null){
            notification.setStatus(NotificationSeenType.READ);
            notificationRepository.save(notification);
        }
    }

    @Override
    public void createNotification(User user, String content, NotificationPost notificationPost) {
        Notification notification = Notification.builder()
                .notificationPost(notificationPost)
                .user(user)
                .content(content)
                .status(NotificationSeenType.NOT_SEEN)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(User user, String content, NotificationFollow notificationFollow) {
        Notification notification = Notification.builder()
                .notificationFollow(notificationFollow)
                .user(user)
                .content(content)
                .status(NotificationSeenType.NOT_SEEN)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public void updateNotification(String content,NotificationPost notificationPost, NotificationSeenType status) {
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


}
