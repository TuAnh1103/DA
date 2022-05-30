package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.bookmark.BookmarkResponse;
import com.viuniteam.socialviuni.service.BookmarkService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{postId}")
    public BookmarkResponse save(@PathVariable("postId") Long postId){
        return bookmarkService.save(postId);
    }

    @DeleteMapping("/{bookmarkId}")
    public void remove(@PathVariable("bookmarkId") Long bookmarkId){
        bookmarkService.remove(bookmarkId);
    }

    @PostMapping
    public Page<BookmarkResponse> findAll(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return bookmarkService.findAll(pageRequest);
    }

}
