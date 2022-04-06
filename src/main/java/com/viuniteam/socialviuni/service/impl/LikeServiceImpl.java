package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.repository.LikeRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.LikeService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


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
        if(post == null || !post.getAuthor().isActive()) throw new ObjectNotFoundException("Bài viết không tồn tại");
        User user = userService.findOneById(profile.getId());
        if(!checkLiked(post,user)){
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);
            throw new OKException("Đã like");
        }
        likeRepository.deleteByUserAndPost(user,post);
        throw new OKException("Đã hủy like");
    }

    @Override
    public boolean checkLiked(Post post, User user) {
        return likeRepository.findOneByPostAndUser(post,user)!=null;
    }
}
