package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/friends/")
public class FriendController {
    private final FriendService friendService;
    private final Profile profile;

//    @PostMapping("/add/{id}")
//    public ResponseEntity<?> addFriend(@PathVariable("id") Long idTarget){
//        return friendService.addFriend(getIdUserName.getId(),idTarget);
//    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeFriend(@PathVariable("id") Long idTarget){
        return friendService.removeFriend(profile.getId(),idTarget);
    }
    @GetMapping("/getall/{id}")
    public ResponseEntity<?> getAllFriend(@PathVariable("id") Long id){
        return friendService.getAll(id);
    }

    @GetMapping("/getall/me")
    public ResponseEntity<?> getAllMyFriend(){
        return friendService.getAll(profile.getId());
    }
}
