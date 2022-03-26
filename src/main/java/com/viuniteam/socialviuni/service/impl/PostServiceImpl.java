package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.mapper.request.post.PostRequestMapper;
import com.viuniteam.socialviuni.mapper.response.post.PostResponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserAuthorResponseMapper;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.FriendService;
import com.viuniteam.socialviuni.service.ImageService;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PostResponseMapper postResponseMapper;
    private final FriendService friendService;
    private final ImageService imageService;
    private final UserAuthorResponseUtils userAuthorResponseUtils;
    @Override
    public ResponseEntity<?> save(PostSaveRequest postSaveRequest) {
        Post post = postRequestMapper.to(postSaveRequest);
        post.setAuthor(userService.findOneById(profile.getId()));

        List<Image> images = listImageFromRequest(postSaveRequest);
        if(images!=null)
            post.setImages(images);

        return convertToPostResponse(post);
    }


    @Override
    public ResponseEntity<?> update(Long id, PostSaveRequest postSaveRequest) {
        Post oldPost = postRepository.findOneById(id);
        if(oldPost == null)
            return new ResponseEntity<>(new JsonException(400,"Bài viết không tồn tại"), HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(new JsonException(400,"Không có quyền sửa bài viết"), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if(postRepository.findOneById(id) == null)
            return new ResponseEntity<>(new JsonException(404,"Bài viết không tồn tại"), HttpStatus.NOT_FOUND);
        if(this.myPost(id)){
            postRepository.deleteById(id);
            return new ResponseEntity<>(new JsonException(200,"Xóa bài viết thành công"), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new JsonException(400,"Không có quyền xóa bài viết"), HttpStatus.BAD_REQUEST);
    }


    @Override
    public List<PostResponse> listPost(Long id) {

        User user = userService.findOneById(id);
        List<Post> listPost = postRepository.findByAuthor(user);
        List<PostResponse> postResponseList = new ArrayList<>();
        listPost.forEach(post -> {
            PostResponse postResponse = postResponseMapper.from(post);
            User author = post.getAuthor();
            postResponse.setAuthorResponse(userAuthorResponseUtils.convert(author));
            if(post.getPrivicy() == 2){ // quyen rieng tu ban be
                if(friendService.isFriend(post.getAuthor().getId(),profile.getId()) || post.getAuthor().getId() == profile.getId()){
                    postResponseList.add(postResponse);
                }
            }
            else if(post.getPrivicy() == 3){ // quyen rieng tu chi minh toi
                if (post.getAuthor().getId() == profile.getId()){
                    postResponseList.add(postResponse);
                }
            }
            else postResponseList.add(postResponse); // quyen rieng tu cong khai

        });
        return postResponseList;
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
    public boolean myPost(Long id) {
        List<Post> postList = postRepository.findByAuthor(userService.findOneById(profile.getId()));
        for(Post post : postList)
            if(post.getId() == id) return true;
        return false;
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

    public ResponseEntity<?> convertToPostResponse(Post post){
        Post postSuccess = postRepository.save(post);
        PostResponse postResponse = postResponseMapper.from(postSuccess);
        User author = postSuccess.getAuthor();
        postResponse.setAuthorResponse(userAuthorResponseUtils.convert(author));
        return ResponseEntity.ok(postResponse);
    }
}
