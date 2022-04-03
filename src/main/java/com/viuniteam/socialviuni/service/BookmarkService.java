package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.bookmark.BookmarkResponse;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;

import java.util.List;

public interface BookmarkService {
    BookmarkResponse save(Long postId);
    void remove(Long bookmarkId);
    List<BookmarkResponse> findAll();
}
