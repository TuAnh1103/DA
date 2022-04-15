package com.viuniteam.socialviuni.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import lombok.Data;

@Data
public class NotificationResponse extends BaseDTO {

    private String content;

    private boolean status;

    @JsonProperty("notification_post")
    private NotificationPostResponse notificationPostResponse;

//    private NotificationFollow notificationFollow;
}
