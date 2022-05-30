package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.newsfeed.NewsFeedResponse;

import java.util.List;

public interface NewsFeedService {
    List<NewsFeedResponse> getNewsFeed();
}
