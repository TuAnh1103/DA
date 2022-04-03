package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.bookmark.BookmarkResponse;
import com.viuniteam.socialviuni.service.BookmarkService;
import lombok.AllArgsConstructor;
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

    @GetMapping
    public List<BookmarkResponse> findAll(){
        return bookmarkService.findAll();
    }

}
