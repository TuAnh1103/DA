package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.friendrequest.FriendRequestResponse;
import com.viuniteam.socialviuni.service.FriendRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/friendrequest/")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("/add/{id}")
    public void addFriendRequest(@PathVariable("id") Long idTarget){
        friendRequestService.addFriendRequest(idTarget);
    }

    @PostMapping("/remove/{id}")
    public void removeFriendRequest(@PathVariable("id") Long idTarget){
        friendRequestService.removeFriendRequest(idTarget);
    }

    @GetMapping("/getall")
    public List<FriendRequestResponse> getAllFriendRequest(){
        return friendRequestService.getAll();
    }
}
