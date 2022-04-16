package com.viuniteam.socialviuni.dto.utils.comment;

import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtilsAdapter;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.mapper.response.comment.CommentResponseMapper;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentResponseUtils extends ResponseUtilsAdapter<Comment, CommentResponse> {

    private final CommentResponseMapper commentResponseMapper;
    private final UserAuthorResponseUtils userAuthorResponseUtils;
    private final ImageReponseMapper imageReponseMapper;
    @Override
    public CommentResponse convert(Comment obj) {
        CommentResponse commentResponse = commentResponseMapper.from(obj);
        commentResponse.setUserAuthorResponse(userAuthorResponseUtils.convert(obj.getUser()));
        commentResponse.setImageResponse(imageReponseMapper.from(ListUtils.getLast(obj.getImages())));
        return commentResponse;
    }

}
