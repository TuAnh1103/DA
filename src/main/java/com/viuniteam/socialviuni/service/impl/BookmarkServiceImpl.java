package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.bookmark.BookmarkResponse;
import com.viuniteam.socialviuni.dto.utils.post.PostResponseUtils;
import com.viuniteam.socialviuni.entity.Bookmark;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.response.bookmark.BookmarkResponseMapper;
import com.viuniteam.socialviuni.repository.BookmarkRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.service.BookmarkService;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final PostRepository postRepository;
    private final PostService postService;
    private final BookmarkRepository bookmarkRepository;
    private final Profile profile;
    private final UserService userService;
    private final BookmarkResponseMapper bookmarkResponseMapper;
    private final PostResponseUtils postResponseUtils;
    @Override
    public BookmarkResponse save(Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post==null || !post.getAuthor().isActive())
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        if(postService.checkPrivacy(post,profile))
            return convertToBookmarkResponse(post);
        throw new BadRequestException("Không có quyền lưu");
    }

    private BookmarkResponse convertToBookmarkResponse(Post post){
        Bookmark bookmark = Bookmark
                .builder()
                .user(userService.findOneById(profile.getId()))
                .post(post)
                .build();
        Bookmark bookmarkSuccess = bookmarkRepository.save(bookmark);
        BookmarkResponse bookmarkResponse= bookmarkResponseMapper.from(bookmarkSuccess);
        bookmarkResponse.setPostResponse(postResponseUtils.convert(bookmarkSuccess.getPost()));
        return bookmarkResponse;
    }

    @Override
    public void remove(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findOneById(bookmarkId);
        if(bookmark==null) throw new ObjectNotFoundException("Bookmark không tồn tại");
        if(bookmark.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
            bookmarkRepository.deleteById(bookmarkId);
            throw new OKException("Xóa bookmark thành công");
        }
        throw new BadRequestException("Không có quyền xóa");
    }

    @Override
    public Page<BookmarkResponse> findAll(Pageable pageable) {
        Page<Bookmark> bookmarks = bookmarkRepository.findAllByUserOrderByIdDesc(userService.findOneById(profile.getId()), pageable);
        List<BookmarkResponse> bookmarkResponses = new ArrayList<>();
        bookmarks.stream().forEach(bookmark -> {
            if(bookmark.getPost().getAuthor().isActive()
                    && postService.checkPrivacy(bookmark.getPost(),profile)) {
                BookmarkResponse bookmarkResponse = bookmarkResponseMapper.from(bookmark);
                bookmarkResponse.setPostResponse(postResponseUtils.convert(bookmark.getPost()));
                bookmarkResponses.add(bookmarkResponse);
            }
        });
        return new PageImpl<>(bookmarkResponses,pageable,bookmarkResponses.size());
    }
}
