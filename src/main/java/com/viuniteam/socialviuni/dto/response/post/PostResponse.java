package com.viuniteam.socialviuni.dto.response.post;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostResponse{
    private Long id;

    @JsonProperty("author")
    private UserAuthorResponse authorResponse;
    private String content;
    private Integer privicy;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("images")
    private List<ImageResponse> images;

}
