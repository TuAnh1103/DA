package com.viuniteam.socialviuni.dto.response.bookmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import lombok.Data;

@Data
public class BookmarkResponse extends BaseDTO {
    @JsonProperty("post")
    private PostResponse postResponse;
}
