package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.offensivekeyword.OffensiveKeywordResponse;
import com.viuniteam.socialviuni.utils.Keyword;

import java.util.List;

public interface OffensiveKeywordService {
    void add(Keyword keyword);
    void remove(Long keywordId);
    void update(Long keywordId, Keyword keyword);
    List<OffensiveKeywordResponse> getAll();
    boolean isExist(String content);
}
