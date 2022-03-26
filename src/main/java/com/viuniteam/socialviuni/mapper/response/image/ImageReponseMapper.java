package com.viuniteam.socialviuni.mapper.response.image;

import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ImageReponseMapper extends Mapper<Image, ImageResponse> {
}
