package com.viuniteam.socialviuni.mapper.response.notification;

import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.entity.Notification;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface NotificationResponseMapper extends Mapper<Notification, NotificationResponse> {
}
