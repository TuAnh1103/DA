package com.viuniteam.socialviuni.mapper.response.follow;

import com.viuniteam.socialviuni.dto.response.follow.FollowResponse;
import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.mapper.Mapper;


@org.mapstruct.Mapper(componentModel = "spring")
public interface FollowerResponseMapper extends Mapper<Follower, FollowResponse> {
}
