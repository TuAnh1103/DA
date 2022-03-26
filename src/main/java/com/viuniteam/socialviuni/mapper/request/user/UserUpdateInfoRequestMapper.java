package com.viuniteam.socialviuni.mapper.request.user;

import com.viuniteam.socialviuni.dto.request.user.UserUpdateInfoRequest;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserUpdateInfoRequestMapper extends Mapper<User, UserUpdateInfoRequest> {
}
