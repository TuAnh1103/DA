package com.viuniteam.socialviuni.controller.admin;

import com.viuniteam.socialviuni.dto.response.offensivekeyword.OffensiveKeywordResponse;
import com.viuniteam.socialviuni.service.OffensiveKeywordService;
import com.viuniteam.socialviuni.utils.Keyword;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/offensive")
public class AdminOffensiveKeywordController {
    private final OffensiveKeywordService offensiveKeywordService;

    @PostMapping
    public void addOffensiveKeyword(@RequestBody Keyword keyword){
        offensiveKeywordService.add(keyword);
    }
    @DeleteMapping("/{keywordId}")
    public void removeOffensiveKeyword(@PathVariable("keywordId") Long keywordId){
        offensiveKeywordService.remove(keywordId);
    }
    @PutMapping("/{keywordId}")
    public void updateOffensiveKeyword(@PathVariable("keywordId") Long keywordId, @RequestBody Keyword keyword){
        offensiveKeywordService.update(keywordId, keyword);
    }
    @GetMapping
    public List<OffensiveKeywordResponse> getAll(){
        return offensiveKeywordService.getAll();
    }
}
