package com.viuniteam.socialviuni.dto.response.newsfeed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import lombok.Data;

@Data
public class NewsFeedResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostResponse postResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ShareResponse shareResponse;
}
