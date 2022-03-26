package com.viuniteam.socialviuni.mapper.response.post;

import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface PostResponseMapper extends Mapper<Post, PostResponse> {
}
