package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.service.BrowserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/browser/")
public class BrowserController {
    private final BrowserService browserService;

    @GetMapping("/{userId}")
    public List<BrowserSaveRequest> getAllBrowserByUser(@PathVariable("userId") Long userId){
        return browserService.getAllByUser(userId);
    }
    @DeleteMapping("/{browserId}")
    public void removeBrowser(@PathVariable("browserId") Long browserId){
        browserService.remove(browserId);
    }
}
