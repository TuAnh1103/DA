package com.viuniteam.socialviuni.dto.utils.post;

import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtils;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.response.post.PostResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostResponseUtils implements ResponseUtils<Post,PostResponse> {
    private PostResponseMapper postResponseMapper;
    private UserAuthorResponseUtils userAuthorResponseUtils;
    @Override
    public PostResponse convert(Post obj) {
        PostResponse postResponse = postResponseMapper.from(obj);
        User author = obj.getAuthor();
        postResponse.setAuthorResponse(userAuthorResponseUtils.convert(author));
        return postResponse;
    }
}
