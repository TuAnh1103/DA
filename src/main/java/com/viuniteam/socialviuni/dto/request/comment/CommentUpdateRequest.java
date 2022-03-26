package com.viuniteam.socialviuni.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentUpdateRequest {

    private Long id;

    private String content;

    @JsonProperty("image_id")
    private Long imageId;
}
