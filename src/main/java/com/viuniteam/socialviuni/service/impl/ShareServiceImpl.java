package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.dto.utils.share.ShareResponseUtils;
import com.viuniteam.socialviuni.entity.*;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.ShareRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationPostRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.service.NotificationService;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.service.ShareService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.ListUtils;
import com.viuniteam.socialviuni.utils.ShortContent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final Profile profile;
    private final ShareResponseUtils shareResponseUtils;
//    private final HandlingOffensive handlingOffensive;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final NotificationPostRepository notificationPostRepository;
    private final PostService postService;
    @Override
    public ShareResponse share(ShareSaveRequest shareSaveRequest, Long postId) {
        //check noi dung tho tuc bai viet share
        //handlingOffensive.handling(shareSaveRequest);

        Post post = postRepository.findOneById(postId);
        if(post==null) throw new ObjectNotFoundException("Bài viết không tồn tại");
        User user = userService.findOneById(profile.getId());
        Share share = Share.builder()
                .post(post)
                .user(user)
                .content(shareSaveRequest.getContent())
                .build();
        Share shareSuccess = shareRepository.save(share);
        ShareResponse shareResponse = shareResponseUtils.convert(shareSuccess);
        createNotification(post,user,NotificationSeenType.NOT_SEEN);

        //create notification to follower
        List<Follower> followers = user.getFollowers();
        followers.stream().forEach(follower -> createNotificationToFollower(follower.getUser(),shareSuccess));

        return shareResponse;
    }

    @Override
    public void remove(Long shareId) {
        Share share = shareRepository.findOneById(shareId);
        if(share==null) throw new ObjectNotFoundException("Bài viết không tồn tại");
        if(share.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
            shareRepository.deleteById(shareId);
            createNotification(share.getPost(),share.getUser(),NotificationSeenType.SEEN);
            throw new OKException("Xóa thành công");
        }
        throw new BadRequestException("Không có quyền xóa");
    }

    @Override
    public Long countSharePost(Long postId) {
        return shareRepository.countByPost(postRepository.findOneById(postId));
    }

    @Override
    public ShareResponse update(ShareSaveRequest shareSaveRequest, Long shareId) {
        Share share = shareRepository.findOneById(shareId);
        if(share==null)
            throw new ObjectNotFoundException("Bài chia sẻ không tồn tại");
        if(share.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
            share.setContent(shareSaveRequest.getContent());
            Share shareUpdate = shareRepository.save(share);
            ShareResponse shareResponse = shareResponseUtils.convert(shareUpdate);
            return shareResponse;
        }
        throw new BadRequestException("Không có quyền update bài chia sẻ");
    }

    @Override
    public Page<ShareResponse> listShare(Long userId, Pageable pageable) {
        User user = userService.findOneById(userId);
        if(user==null) throw new ObjectNotFoundException("Tài khoản không tồn tại");
        if(user.isActive() || (!user.isActive() && userService.isAdmin(profile))){ // tai khoan hoat dong, neu k hoat dong thi chi admin moi dc xem
            Page<Share> shares = shareRepository.findAllByUserOrderByIdDesc(user,pageable);
            List<ShareResponse> shareResponseList = new ArrayList<>();
            shares.stream().forEach(share -> {
                if(postService.checkPrivacy(share.getPost(),profile)){
                    ShareResponse shareResponse = shareResponseUtils.convert(share);
                    shareResponseList.add(shareResponse);
                }
            });
            return new PageImpl<>(shareResponseList, pageable, shareResponseList.size());
        }
        return null;
    }

    @Override
    public ShareResponse newShare(Long userId) {
        Share share = ListUtils.getFirst(shareRepository.findByUserOrderByIdDesc(userService.findOneById(userId)));
        return share==null ? null : shareResponseUtils.convert(share);
    }


    private void createNotificationShare(Post post,User user){
        NotificationPost notificationPost = NotificationPost.builder()
                .notificationPostType(NotificationPostType.SHARE)
                .post(post)
                .build();

        notificationService.createNotification(
                post.getAuthor(),
                user.getLastName()+" "+user.getFirstName()+" đã chia sẻ bài viết: "+ ShortContent.convertToShortContent(post.getContent()),
                notificationPost);
    }

    private void updateNotificationShare(Post post, NotificationSeenType status){
        Long shareCount = shareRepository.countByPostGroupByUser(post.getId());
        if(shareCount > 0){
            User userNewShare = shareRepository.findTop1ByPostOrderByCreatedDateDesc(post).getUser();

            String fullName=userNewShare.getLastName()+" "+userNewShare.getFirstName();

            String postShortContent = ShortContent.convertToShortContent(post.getContent());
            String content;

            if(shareCount == 1)
                content = fullName+" đã chia sẻ bài viết: "+postShortContent;
            else
                content = fullName +" và "+(shareCount-1) +" người khác đã chia sẻ bài viết: "+postShortContent;

            NotificationPost notificationPost = notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.SHARE);
            notificationService.updateNotification(content,notificationPost,status);
        }
    }

    private void createNotification(Post post, User user, NotificationSeenType status){
        if(notificationRepository.findOneByNotificationPost( // check if notification like not exist then create notification
                notificationPostRepository.findOneByPostAndNotificationPostType(post,NotificationPostType.SHARE))==null)
            createNotificationShare(post,user);
        else
            updateNotificationShare(post,status); // update notification
    }

    private void createNotificationToFollower(User user,Share share){
        NotificationFollow notificationFollow = NotificationFollow.builder()
                .share(share)
                .build();
        notificationService.createNotification(user,share.getUser().getLastName()+" "+share.getUser().getFirstName()+" đã chia sẻ bài viết: "
                + ShortContent.convertToShortContent(share.getPost().getContent()),notificationFollow);
    }
}
