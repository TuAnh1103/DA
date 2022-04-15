package com.viuniteam.socialviuni.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import lombok.Data;

@Data
public class NotificationPostResponse {

    @JsonProperty("notification_post_type")
    private NotificationPostType notificationPostType;

    @JsonProperty("post_id")
    private Long postId;
}
