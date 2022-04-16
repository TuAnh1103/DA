package com.viuniteam.socialviuni.dto.utils.post;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtilsAdapter;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.response.post.PostResponseMapper;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.LikeRepository;
import com.viuniteam.socialviuni.repository.ShareRepository;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostResponseUtils extends ResponseUtilsAdapter<Post,PostResponse> {
    private final PostResponseMapper postResponseMapper;
    private final UserAuthorResponseUtils userAuthorResponseUtils;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ShareRepository shareRepository;
    private final UserService userService;
    private final Profile profile;
    @Override
    public PostResponse convert(Post obj) {
        PostResponse postResponse = postResponseMapper.from(obj);
        User author = obj.getAuthor();
        postResponse.setAuthorResponse(userAuthorResponseUtils.convert(author));
        postResponse.setLikeCount(likeRepository.countByPostAndStatus(obj, true));
        postResponse.setCommentCount(commentRepository.countByPost(obj));
        postResponse.setShareCount(shareRepository.countByPost(obj));
        postResponse.setLiked(likeRepository.existsByPostAndUserAndStatus(obj,userService.findOneById(profile.getId()),true));
        return postResponse;
    }
}
