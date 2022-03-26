package com.viuniteam.socialviuni.mapper.request.post;

import com.viuniteam.socialviuni.dto.request.post.PostSaveRequest;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface PostRequestMapper extends Mapper<Post, PostSaveRequest> {
}
