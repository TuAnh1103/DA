package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.dto.response.browser.BrowserResponse;
import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.entity.User;

import java.util.List;

public interface BrowserService {
    void save(BrowserSaveRequest browserSaveRequest);
    void deleteAllByUser(Long userId);
    List<BrowserResponse> getAllByUser(Long userId);
}
