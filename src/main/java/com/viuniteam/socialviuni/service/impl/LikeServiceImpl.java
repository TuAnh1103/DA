package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.repository.LikeRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.LikeService;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final Profile profile;
    private final UserService userService;

    @Override
    public void like(Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post!=null){
            User user = userService.findOneById(profile.getId());
            if(!checkLiked(post,user)){
                Like like = Like.builder()
                        .post(post)
                        .user(user)
                        .build();
                likeRepository.save(like);
            }
        }
    }
    @Override
    public void removeLike(Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post!=null){
            User user = userService.findOneById(profile.getId());
            if(checkLiked(post,user))
                likeRepository.deleteByUserAndPost(user,post);
        }
    }

    @Override
    public boolean checkLiked(Post post, User user) {
        List<Like> likeList = post.getLikes();
        for(Like like : likeList){
            if(like.getUser().getId().equals(user.getId()))
                return true;
        }
        return false;
    }

    @Override
    public Long countLikePost(Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post!=null){
            return likeRepository.countByPost(post);
        }
        return 0L;
    }
}
