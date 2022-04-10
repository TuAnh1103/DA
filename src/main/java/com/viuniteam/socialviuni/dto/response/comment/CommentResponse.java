package com.viuniteam.socialviuni.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import lombok.Data;

import java.util.Date;

@Data
public class CommentResponse extends BaseDTO {
    private String content;

    @JsonProperty("author")
    private UserAuthorResponse userAuthorResponse;

    @JsonProperty("image")
    private ImageResponse imageResponse;

}
