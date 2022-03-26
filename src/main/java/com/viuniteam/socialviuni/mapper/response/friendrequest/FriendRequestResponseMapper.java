package com.viuniteam.socialviuni.mapper.response.friendrequest;

import com.viuniteam.socialviuni.dto.response.friendrequest.FriendRequestResponse;
import com.viuniteam.socialviuni.entity.FriendRequest;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface FriendRequestResponseMapper extends Mapper<FriendRequest, FriendRequestResponse> {
}
