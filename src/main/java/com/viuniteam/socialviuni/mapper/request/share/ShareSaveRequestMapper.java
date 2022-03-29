package com.viuniteam.socialviuni.mapper.request.share;

import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ShareSaveRequestMapper extends Mapper<Share, ShareSaveRequest> {
}
