package com.viuniteam.socialviuni.mapper.response.comment;

import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CommentResponseMapper extends Mapper<Comment, CommentResponse> {
}
