package com.viuniteam.socialviuni.mapper.response.bookmark;

import com.viuniteam.socialviuni.dto.response.bookmark.BookmarkResponse;
import com.viuniteam.socialviuni.entity.Bookmark;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface BookmarkResponseMapper extends Mapper<Bookmark, BookmarkResponse> {
}
