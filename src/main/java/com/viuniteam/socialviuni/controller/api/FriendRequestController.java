package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.service.FriendRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/friendrequest/")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addFriendRequest(@PathVariable("id") Long idTarget){
        return friendRequestService.addFriendRequest(idTarget);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeFriendRequest(@PathVariable("id") Long idTarget){
        return friendRequestService.removeFriendRequest(idTarget);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllFriendRequest(){
        return friendRequestService.getAll();
    }
}
