package com.viuniteam.socialviuni.dto.utils.share;

import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtils;
import com.viuniteam.socialviuni.dto.utils.ResponseUtilsAdapter;
import com.viuniteam.socialviuni.dto.utils.post.PostResponseUtils;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.mapper.response.share.ShareResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ShareResponseUtils extends ResponseUtilsAdapter<Share, ShareResponse> {
    private final ShareResponseMapper shareResponseMapper;
    private final PostResponseUtils postResponseUtils;
    private final UserAuthorResponseUtils userAuthorResponseUtils;
    @Override
    public ShareResponse convert(Share obj) {
        ShareResponse shareResponse = shareResponseMapper.from(obj);
        shareResponse.setUserAuthorResponse(userAuthorResponseUtils.convert(obj.getUser()));
        shareResponse.setPostResponse(postResponseUtils.convert(obj.getPost()));
        return shareResponse;
    }

}
