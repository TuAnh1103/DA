package com.viuniteam.socialviuni.mapper.request.comment;

import com.viuniteam.socialviuni.dto.request.comment.CommentSaveRequest;
import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CommentRequestMapper extends Mapper<Comment, CommentSaveRequest> {
}
