package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/follow/")
public class FollowController {
    private final FollowService followService;
    private final Profile profile;

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addFollow(@PathVariable("id") Long id){
        return followService.addFollow(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeFollow(@PathVariable("id") Long id){
        return followService.removeFollow(id);
    }

    @GetMapping("/follower/{id}")
    public ResponseEntity<?> getAllFollower(@PathVariable("id") Long id){
        return followService.getAllFollower(id);
    }

    @GetMapping("/follower/me")
    public ResponseEntity<?> getAllFollower(){
        return followService.getAllFollower(profile.getId());
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<?> getAllFollowing(@PathVariable("id") Long id){
        return followService.getAllFollowing(id);
    }

    @GetMapping("/following/me")
    public ResponseEntity<?> getAllFollowing(){
        return followService.getAllFollowing(profile.getId());
    }


}
