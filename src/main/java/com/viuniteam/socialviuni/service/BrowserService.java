package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.entity.User;

import java.util.List;

public interface BrowserService {
    void save(BrowserSaveRequest browserSaveRequest);
    void remove(Long browserId);
    List<BrowserSaveRequest> getAllByUser(Long userId);
}
