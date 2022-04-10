package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.bookmark.BookmarkResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookmarkService {
    BookmarkResponse save(Long postId);
    void remove(Long bookmarkId);
    Page<BookmarkResponse> findAll(Pageable pageable);
}
