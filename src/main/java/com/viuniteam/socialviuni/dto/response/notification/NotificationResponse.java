package com.viuniteam.socialviuni.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.entity.NotificationFollow;
import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
import lombok.Data;

@Data
public class NotificationResponse extends BaseDTO {

    private String content;

    private NotificationSeenType status;

    @JsonProperty("notification_post")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private NotificationPostResponse notificationPostResponse;

    @JsonProperty("notification_follow")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private NotificationFollowResponse notificationFollowResponse;
}
