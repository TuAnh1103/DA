package com.viuniteam.socialviuni.dto.response.share;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import lombok.Data;


@Data
public class ShareResponse extends BaseDTO {
    private String content;
    @JsonProperty("author_share")
    private UserAuthorResponse userAuthorResponse;
    @JsonProperty("author_post")
    private PostResponse postResponse;
}
