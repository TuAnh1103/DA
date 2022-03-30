package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtils;
import com.viuniteam.socialviuni.dto.utils.share.ShareResponseUtils;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.mapper.response.share.ShareResponseMapper;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.ShareRepository;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.service.ShareService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final Profile profile;
    private final PostService postService;
    private final ShareResponseUtils shareResponseUtils;
    @Override
    public ResponseEntity<?> share(ShareSaveRequest shareSaveRequest, Long postId) {
        Post post = postRepository.findOneById(postId);
        if(post!=null){
            User user = userService.findOneById(profile.getId());
            Share share = Share.builder()
                        .post(post)
                        .user(user)
                        .content(shareSaveRequest.getContent())
                        .build();
            Share shareSuccess = shareRepository.save(share);
            ShareResponse shareResponse = shareResponseUtils.convert(shareSuccess);
            return new ResponseEntity<>(shareResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new JsonException(404,"Bài viết không tồn tại"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> removeShare(Long shareId) {
        Share share = shareRepository.findOneById(shareId);
        if(share != null){
            if(share.getUser().getId().equals(profile.getId()) || userService.isAdmin(profile)){
                shareRepository.deleteById(shareId);
                return ResponseEntity.ok(new JsonException(200,"Xóa thành công"));
            }
            return new ResponseEntity<>(new JsonException(400,"Không có quyền xóa"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new JsonException(404,"Bài viết không tồn tại"),HttpStatus.NOT_FOUND);
    }

    @Override
    public Long countSharePost(Long postId) {
        return shareRepository.countByPost(postRepository.findOneById(postId));
    }
}
