package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.utils.post.PostResponseUtils;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.PrivicyPostType;
import com.viuniteam.socialviuni.exception.*;
import com.viuniteam.socialviuni.mapper.request.post.PostRequestMapper;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostRequestMapper postRequestMapper;
    private final Profile profile;
    private final UserService userService;
    private final FriendService friendService;
    private final ImageService imageService;
    private final PostResponseUtils postResponseUtils;
    private final OffensiveKeywordService offensiveKeywordService;
    @Override
    public PostResponse save(PostSaveRequest postSaveRequest) {
        if(offensiveKeywordService.isExist(postSaveRequest.getContent()))
            throw new BadRequestException("Bài viết không được chứa từ ngữ thô tục");
        Post post = postRequestMapper.to(postSaveRequest);
        post.setAuthor(userService.findOneById(profile.getId()));

        List<Image> images = listImageFromRequest(postSaveRequest);
        if(images!=null)
            post.setImages(images);
        return convertToPostResponse(post);
    }

    @Override
    public PostResponse update(Long id, PostSaveRequest postSaveRequest) {
        if(offensiveKeywordService.isExist(postSaveRequest.getContent()))
            throw new BadRequestException("Bài viết không được chứa từ ngữ thô tục");
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
            return convertToPostResponse(newPost);
        }
        throw new BadRequestException("Không có quyền sửa bài viết");
    }

    @Override
    public void delete(Long id) {
        if(postRepository.findOneById(id) == null)
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        if(this.myPost(id) || userService.isAdmin(profile)){
            postRepository.deleteById(id);
            throw new OKException("Xóa bài viết thành công");
        }
        throw new BadRequestException("Không có quyền xóa bài viết");
    }


    @Override
    public Page<PostResponse> listPost(Long userId, Pageable pageable) {
        User user = userService.findOneById(userId);
        if(user==null) throw new ObjectNotFoundException("Tài khoản không tồn tại");
        if(user.isActive() || (!user.isActive() && userService.isAdmin(profile))){ // tai khoan hoat dong, neu k hoat dong thi chi admin moi dc xem
            Page<Post> posts = postRepository.findAllByAuthorOrderByIdDesc(user,pageable);
            List<PostResponse> postResponseList = new ArrayList<>();
            posts.stream().forEach(post -> {
                if(checkPrivicy(post,profile)){
                    PostResponse postResponse = postResponseUtils.convert(post);
                    postResponseList.add(postResponse);
                }
            });
            return new PageImpl<>(postResponseList, pageable, postResponseList.size());
        }
        return null;
    }

    @Override
    public PostResponse findOneById(Long id) {
        Post post = postRepository.findOneById(id);
        if(post == null || !checkPrivicy(post,profile)) throw new ObjectNotFoundException("Bài viết không tồn tại");
        return postResponseUtils.convert(post);
    }


    @Override
    public void autoCreatePost(String content, List<Image> images) {
        Post post = Post.builder()
                .author(userService.findOneById(profile.getId()))
                .content(content)
                .privicy(1)
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
    public boolean checkPrivicy(Post post, Profile profile) { // check quyen rieng tu bai viet
        if(post.getPrivicy() == PrivicyPostType.FRIEND.getCode()){ // quyen rieng tu ban be
            if(friendService.isFriend(post.getAuthor().getId(),profile.getId())
                    || post.getAuthor().getId().equals(profile.getId())
                    || userService.isAdmin(profile))
                return true;

            return false;
        }
        else if(post.getPrivicy() == PrivicyPostType.ONLY_ME.getCode()){ // quyen rieng tu chi minh toi
            if (post.getAuthor().getId() == profile.getId() || userService.isAdmin(profile))
                return true;

            return false;
        }
        else                // quyen rieng tu cong khai
            return true;
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

    public PostResponse convertToPostResponse(Post post){
        Post postSuccess = postRepository.save(post);
        return postResponseUtils.convert(postSuccess);
    }
}
