package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.post.PostFilterRequest;
import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.utils.post.PostResponseUtils;
import com.viuniteam.socialviuni.entity.*;
import com.viuniteam.socialviuni.enumtype.PrivicyPostType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.request.post.PostRequestMapper;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationFollowRepository;
import com.viuniteam.socialviuni.repository.notification.NotificationRepository;
import com.viuniteam.socialviuni.repository.specification.PostSpecification;
import com.viuniteam.socialviuni.service.*;
import com.viuniteam.socialviuni.utils.ListUtils;
import com.viuniteam.socialviuni.utils.ShortContent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final PostRequestMapper postRequestMapper;
    private final Profile profile;
    private final UserService userService;
    private final FriendService friendService;
    private final ImageService imageService;
    private final PostResponseUtils postResponseUtils;
    private final NotificationService notificationService;
    private final NotificationFollowRepository notificationFollowRepository;
//    private final HandlingOffensive handlingOffensive;
    @Override
    public PostResponse save(PostSaveRequest postSaveRequest) {
        //check noi dung tho tuc noi dung bai viet
        //handlingOffensive.handling(postSaveRequest);

        Post post = postRequestMapper.to(postSaveRequest);

        User author = userService.findOneById(profile.getId());
        post.setAuthor(author);

        List<Image> images = listImageFromRequest(postSaveRequest);
        if(images!=null)
            post.setImages(images);

        Post postSuccess = postRepository.save(post);

        //create notification to follower
        List<Follower> followers = author.getFollowers();
        followers.stream()
                .filter(follower -> checkPrivacy(postSuccess, follower.getUser().getId())==true)
                .forEach(follower -> createNotificationToFollower(follower.getUser(),postSuccess));

        return postResponseUtils.convert(postSuccess);
    }

    @Override
    public PostResponse update(Long id, PostSaveRequest postSaveRequest) {
        //check noi dung tho tuc noi dung bai viet
        //handlingOffensive.handling(postSaveRequest);

        Post oldPost = postRepository.findOneById(id);
        if(oldPost == null)
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        if(this.myPost(id)){
            Post newPost = postRequestMapper.to(postSaveRequest);
            newPost.setId(id);
            newPost.setAuthor(oldPost.getAuthor());
            newPost.setCreatedDate(oldPost.getCreatedDate());
            List<Image> images = listImageFromRequest(postSaveRequest);
            if(images!=null)
                newPost.setImages(images);
            Post postUpdate = postRepository.save(newPost);
            return postResponseUtils.convert(postUpdate);
        }
        throw new BadRequestException("Không có quyền sửa bài viết");
    }

    @Override
    public void delete(Long id) {
        if(postRepository.findOneById(id) == null)
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        if(this.myPost(id) || userService.isAdmin(profile)){

            notificationRepository.deleteNotificationByNotificationPostAndPostId(id);
            notificationRepository.deleteNotificationByNotificationFollowAndPostId(id);
            notificationRepository.deleteNotificationPostByPostId(id);
            notificationRepository.deleteNotificationFollowByPostId(id);

            postRepository.deleteById(id);
            throw new OKException("Xóa bài viết thành công");
        }
        throw new BadRequestException("Không có quyền xóa bài viết");
    }


    @Override
    public Page<PostResponse> findAllByUserId(Long userId, Pageable pageable) {
        User user = userService.findOneById(userId);
        if(user==null) throw new ObjectNotFoundException("Tài khoản không tồn tại");
        if(user.isActive() || (!user.isActive() && userService.isAdmin(profile))){ // tai khoan hoat dong, neu k hoat dong thi chi admin moi dc xem
            Page<Post> posts = postRepository.findAllByAuthorOrderByIdDesc(user,pageable);
            List<PostResponse> postResponseList = new ArrayList<>();
            posts.stream().forEach(post -> {
                if(checkPrivacy(post,profile)){
                    PostResponse postResponse = postResponseUtils.convert(post);
                    postResponseList.add(postResponse);
                }
            });
            return new PageImpl<>(postResponseList, pageable, postResponseList.size());
        }
        return null;
    }

    @Override
    public Page<PostResponse> search(PostFilterRequest postFilterRequest) {
        PageRequest pageRequest = PageRequest.of(postFilterRequest.getIndex(), postFilterRequest.getSize());
        Page<Post> posts = postRepository.findAll(PostSpecification.filterAll(postFilterRequest),pageRequest);
        if(userService.isAdmin(profile))
            return posts.map(postResponseUtils::convert);
        else{
            List<PostResponse> postResponseList = posts.stream()
                    .filter(post -> checkPrivacy(post,profile))
                    .map(postResponseUtils::convert)
                    .collect(Collectors.toList());
            return new PageImpl<>(postResponseList,pageRequest,postResponseList.size());
        }
    }

    @Override
    public PostResponse findOneById(Long id) {
        Post post = postRepository.findOneById(id);
        if(post == null || !checkPrivacy(post,profile)) throw new ObjectNotFoundException("Bài viết không tồn tại");
        return postResponseUtils.convert(post);
    }


    @Override
    public void autoCreatePost(String content, List<Image> images) {
        Post post = Post.builder()
                .author(userService.findOneById(profile.getId()))
                .content(content)
                .privacy(1)
                .images(images)
                .build();
        postRepository.save(post);
    }

    @Override
    public boolean myPost(Long postId) {
        Post post = postRepository.findOneByAuthorAndId(userService.findOneById(profile.getId()),postId);
        return post!=null;
    }

    @Override
    public boolean checkPrivacy(Post post, Profile profile) { // check quyen rieng tu bai viet
        if(post.getPrivacy() == PrivicyPostType.FRIEND.getCode()){ // quyen rieng tu ban be
            if(friendService.isFriend(post.getAuthor().getId(),profile.getId())
                    || post.getAuthor().getId().equals(profile.getId())
                    || userService.isAdmin(profile))
                return true;

            return false;
        }
        else if(post.getPrivacy() == PrivicyPostType.ONLY_ME.getCode()){ // quyen rieng tu chi minh toi
            if (post.getAuthor().getId() == profile.getId() || userService.isAdmin(profile))
                return true;

            return false;
        }
        else                // quyen rieng tu cong khai
            return true;
    }

    @Override
    public boolean checkPrivacy(Post post, Long userId) {
        if(post.getPrivacy() == PrivicyPostType.FRIEND.getCode()){ // quyen rieng tu ban be
            if(friendService.isFriend(post.getAuthor().getId(),userId))
                return true;
            return false;
        }
        else if(post.getPrivacy() == PrivicyPostType.ONLY_ME.getCode()) // quyen rieng tu chi minh toi
            return false;
        else                // quyen rieng tu cong khai
            return true;
    }

    @Override
    public PostResponse newPost(Long userId) {
        Post post = ListUtils.getFirst(postRepository.findByAuthorOrderByIdDesc(userService.findOneById(userId)));
        return post==null ? null : postResponseUtils.convert(post);
    }

    public List<Image> listImageFromRequest(PostSaveRequest postSaveRequest){
        if(postSaveRequest.getImageIds()!=null){
            List<Image> images = new ArrayList<>();
            postSaveRequest.getImageIds().forEach(imageId->{
                Image image = imageService.findOneById(imageId);
                if(image!=null)
                    images.add(image);
            });
            return images;
        }
        return null;
    }

    private void createNotificationToFollower(User user,Post post){
        NotificationFollow notificationFollow = NotificationFollow.builder()
                .post(post)
                .build();
        notificationService.createNotification(user,post.getAuthor().getLastName()+" "+post.getAuthor().getFirstName()+" đã thêm mới bài viết: "
                + ShortContent.convertToShortContent(post.getContent()),notificationFollow);
    }
}
