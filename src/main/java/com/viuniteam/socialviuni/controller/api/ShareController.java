package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.share.ShareSaveRequest;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.service.ShareService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/share")
public class ShareController {
    private final ShareService shareService;
    private final Profile profile;

    @PostMapping("/{postId}")
    public ShareResponse sharePost(@Valid @RequestBody ShareSaveRequest shareSaveRequest, @PathVariable("postId") Long postId){
        return shareService.share(shareSaveRequest,postId);
    }
    @PostMapping("/delete/{shareId}")
    public void removeSharePost(@PathVariable("shareId") Long shareId){
        shareService.remove(shareId);
    }

    @PostMapping("/update/{shareId}")
    public ShareResponse updateSharePost(@Valid @RequestBody ShareSaveRequest shareSaveRequest, @PathVariable("shareId") Long shareId){
        return shareService.update(shareSaveRequest,shareId);
    }

    @PostMapping("/all/{userId}")
    public Page<ShareResponse> getAllByUser(@PathVariable("userId") Long userId, @RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return shareService.listShare(userId,pageRequest);
    }

    @PostMapping("/all/me")
    public Page<ShareResponse> getAllByMe(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return shareService.listShare(profile.getId(),pageRequest);
    }
}
