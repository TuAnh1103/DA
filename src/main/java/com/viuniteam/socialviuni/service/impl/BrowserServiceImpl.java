package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.request.browser.BrowserRequestMapper;
import com.viuniteam.socialviuni.repository.BrowserRepository;
import com.viuniteam.socialviuni.service.BrowserService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrowserServiceImpl implements BrowserService {
    private final Profile profile;
    private final BrowserRepository browserRepository;
    private final BrowserRequestMapper browserRequestMapper;
    private final UserService userService;
    @Override
    public void save(BrowserSaveRequest browserSaveRequest) {
        Browser browser = browserRequestMapper.to(browserSaveRequest);
        browser.setUser(userService.findOneById(profile.getId()));
        browserRepository.save(browser);
    }

    @Override
    public void remove(Long browserId) {
        browserRepository.delete(browserRepository.findOneById(browserId));
    }

    @Override
    public List<BrowserSaveRequest> getAllByUser(Long userId) {
        return browserRequestMapper.from(browserRepository.findAllByUser(userService.findOneById(userId)));
    }
}
