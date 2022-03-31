package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.follow.FollowResponse;
import com.viuniteam.socialviuni.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/follow/")
public class FollowController {
    private final FollowService followService;
    private final Profile profile;

    @PostMapping("/add/{id}")
    public void addFollow(@PathVariable("id") Long id){
         followService.addFollow(id);
    }

    @DeleteMapping("/remove/{id}")
    public void removeFollow(@PathVariable("id") Long id){
        followService.removeFollow(id);
    }

    @GetMapping("/follower/{id}")
    public List<FollowResponse> getAllFollower(@PathVariable("id") Long id){
        return followService.getAllFollower(id);
    }

    @GetMapping("/follower/me")
    public List<FollowResponse> getAllFollower(){
        return followService.getAllFollower(profile.getId());
    }

    @GetMapping("/following/{id}")
    public List<FollowResponse> getAllFollowing(@PathVariable("id") Long id){
        return followService.getAllFollowing(id);
    }

    @GetMapping("/following/me")
    public List<FollowResponse> getAllFollowing(){
        return followService.getAllFollowing(profile.getId());
    }


}
