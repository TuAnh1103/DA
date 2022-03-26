package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.ShareRepository;
import com.viuniteam.socialviuni.service.ShareService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final Profile profile;
    @Override
    public void share(Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post!=null){
            User user = userService.findOneById(profile.getId());
                Share share = Share.builder()
                        .post(post)
                        .user(user)
                        .build();
                shareRepository.save(share);
        }
    }

    @Override
    public void removeShare(Long postId) {

    }

    @Override
    public Long countSharePost(Long postId) {
        return null;
    }
}
