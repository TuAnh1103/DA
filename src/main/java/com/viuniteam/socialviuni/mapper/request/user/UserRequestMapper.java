package com.viuniteam.socialviuni.mapper.request.user;

import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.Mapper;


@org.mapstruct.Mapper(componentModel = "spring")
public interface UserRequestMapper extends Mapper<User, UserSaveRequest> {

}
