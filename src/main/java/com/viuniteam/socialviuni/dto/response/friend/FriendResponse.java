package com.viuniteam.socialviuni.dto.response.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import lombok.Data;

import java.util.Date;

@Data
public class FriendResponse extends BaseDTO {
    @JsonProperty("user_info")
    private UserInfoResponse userInfoResponse;
}
