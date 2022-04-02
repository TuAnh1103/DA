package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.dto.response.browser.BrowserResponse;
import com.viuniteam.socialviuni.service.BrowserService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/browser")
public class BrowserController {
    private final BrowserService browserService;
    private final Profile profile;
    @GetMapping
    public List<BrowserResponse> getAllBrowserByUser(){
        return browserService.getAllByUser(profile.getId());
    }
    @DeleteMapping
    public void removeAllBrowserByUser(){
        browserService.deleteAllByUser(profile.getId());
    }
}
