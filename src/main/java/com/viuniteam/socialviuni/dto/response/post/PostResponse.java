package com.viuniteam.socialviuni.dto.response.post;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse extends BaseDTO {
    @JsonProperty("author")
    private UserAuthorResponse authorResponse;
    private String content;
    private Integer privacy;

    @JsonProperty("images")
    private List<ImageResponse> images;

    @JsonProperty("like_count")
    private Long likeCount;

    @JsonProperty("cmt_count")
    private Long commentCount;

    @JsonProperty("share_count")
    private Long shareCount;

    private Boolean liked;

}
