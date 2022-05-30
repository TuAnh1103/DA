package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.newsfeed.NewsFeedResponse;
import com.viuniteam.socialviuni.service.NewsFeedService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/newsfeed")
public class NewsFeedController {
    private final NewsFeedService newsFeedService;
    @GetMapping
    public List<NewsFeedResponse> getNewsFeed(){
        return newsFeedService.getNewsFeed();
    }
}
