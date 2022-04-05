package com.viuniteam.socialviuni.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import lombok.Builder;
import lombok.Data;

@Data
public class UserAuthorResponse {
    private Long id;

    private String username;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("avatar_image")
    private ImageResponse avatar;

//    @JsonProperty("cover_image")
//    private ImageResponse cover;
}
