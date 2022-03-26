package com.viuniteam.socialviuni.dto.response.friendrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import lombok.Data;

import java.util.Date;

@Data
public class FriendRequestResponse {
    private Long id;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;
}
