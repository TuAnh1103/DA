package com.viuniteam.socialviuni.mapper.request.browser;

import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface BrowserRequestMapper extends Mapper<Browser, BrowserSaveRequest> {
}
