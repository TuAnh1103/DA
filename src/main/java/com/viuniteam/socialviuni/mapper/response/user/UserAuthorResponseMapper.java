package com.viuniteam.socialviuni.mapper.response.user;

import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserAuthorResponseMapper extends Mapper<User, UserAuthorResponse> {
}
