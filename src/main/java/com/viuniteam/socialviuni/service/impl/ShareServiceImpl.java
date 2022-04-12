package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.annotation.offensivekeyword.HandlingOffensive;
import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.dto.utils.share.ShareResponseUtils;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.ShareRepository;
import com.viuniteam.socialviuni.service.ShareService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final Profile profile;
    private final ShareResponseUtils shareResponseUtils;
//    private final HandlingOffensive handlingOffensive;
    @Override
    public ShareResponse share(ShareSaveRequest shareSaveRequest, Long postId) {
        //check noi dung tho tuc bai viet share
        //handlingOffensive.handling(shareSaveRequest);

        Post post = postRepository.findOneById(postId);
        if(post==null) throw new ObjectNotFoundException("Bài viết không tồn tại");
        User user = userService.findOneById(profile.getId());
        Share share = Share.builder()
                .post(post)
                .user(user)
                .content(shareSaveRequest.getContent())
                .build();
        Share shareSuccess = shareRepository.save(share);
        ShareResponse shareResponse = shareResponseUtils.convert(shareSuccess);
        return shareResponse;
    }

    @Override
    public void removeShare(Long shareId) {
        Share share = shareRepository.findOneById(shareId);
        if(share==null) throw new ObjectNotFoundException("Bài viết không tồn tại");
        if(share.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
            shareRepository.deleteById(shareId);
            throw new OKException("Xóa thành công");
        }
        throw new BadRequestException("Không có quyền xóa");
    }

    @Override
    public Long countSharePost(Long postId) {
        return shareRepository.countByPost(postRepository.findOneById(postId));
    }
}
