package com.viuniteam.socialviuni.dto.utils.post;

import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtils;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.response.post.PostResponseMapper;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.LikeRepository;
import com.viuniteam.socialviuni.service.CommentService;
import com.viuniteam.socialviuni.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PostResponseUtils implements ResponseUtils<Post,PostResponse> {
    private final PostResponseMapper postResponseMapper;
    private final UserAuthorResponseUtils userAuthorResponseUtils;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    @Override
    public PostResponse convert(Post obj) {
        PostResponse postResponse = postResponseMapper.from(obj);
        User author = obj.getAuthor();
        postResponse.setAuthorResponse(userAuthorResponseUtils.convert(author));
        postResponse.setLikeCount(likeRepository.countByPost(obj));
        postResponse.setCommentCount(commentRepository.countByPost(obj));
        return postResponse;
    }

    @Override
    public List<PostResponse> convert(List<Post> obj) {
        return null;
    }
}
