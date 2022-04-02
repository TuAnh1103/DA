package com.viuniteam.socialviuni.mapper.response.browser;

import com.viuniteam.socialviuni.dto.response.browser.BrowserResponse;
import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.mapper.Mapper;
import lombok.Data;

@org.mapstruct.Mapper(componentModel = "spring")
public interface BrowserResponseMapper extends Mapper<Browser, BrowserResponse> {
}
