package com.viuniteam.socialviuni.mapper.response.offensivekeyword;

import com.viuniteam.socialviuni.dto.response.offensivekeyword.OffensiveKeywordResponse;
import com.viuniteam.socialviuni.entity.OffensiveKeyword;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface OffensiveKeywordResponseMapper extends Mapper<OffensiveKeyword, OffensiveKeywordResponse> {
}
