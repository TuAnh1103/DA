package com.viuniteam.socialviuni.mapper.response.share;

import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ShareResponseMapper extends Mapper<Share, ShareResponse> {
}
