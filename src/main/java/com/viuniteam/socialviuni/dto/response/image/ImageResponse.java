package com.viuniteam.socialviuni.dto.response.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageResponse {
    private Long id;

    @JsonProperty("link_image")
    private String linkImage;
}
