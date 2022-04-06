package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.dto.response.browser.BrowserResponse;
import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.mapper.request.browser.BrowserRequestMapper;
import com.viuniteam.socialviuni.mapper.response.browser.BrowserResponseMapper;
import com.viuniteam.socialviuni.repository.BrowserRepository;
import com.viuniteam.socialviuni.service.BrowserService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrowserServiceImpl implements BrowserService {
    private final BrowserRepository browserRepository;
    private final BrowserRequestMapper browserRequestMapper;
    private final BrowserResponseMapper browserResponseMapper;
    private final UserService userService;
    @Override
    public void save(BrowserSaveRequest browserSaveRequest) {
        Browser browser = browserRequestMapper.to(browserSaveRequest);
        browserRepository.save(browser);
    }

    @Override
    public void deleteAllByUser(Long userId) {
        browserRepository.deleteAllByUser(userService.findOneById(userId));
        throw new OKException("Xóa thành công");
    }


    @Override
    public List<BrowserResponse> getAllByUser(Long userId) {
        return browserResponseMapper.from(browserRepository.findAllByUser(userService.findOneById(userId)));
    }
}
