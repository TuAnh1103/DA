package com.viuniteam.socialviuni.mapper.response.friend;

import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface FriendResponseMapper extends Mapper<Friend, FriendResponse> {
}
