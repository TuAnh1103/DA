package com.viuniteam.socialviuni.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import lombok.Data;

import java.util.Date;

@Data
public class CommentResponse{

    private Long id;

    private String content;

    @JsonProperty("author")
    private UserAuthorResponse userAuthorResponse;

    private ImageResponse imageResponse;

    @JsonProperty("created_date")
    private Date createdDate;
}
