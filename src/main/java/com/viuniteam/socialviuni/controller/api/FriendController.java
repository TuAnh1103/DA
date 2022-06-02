package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.friend.FriendResponse;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.service.FriendService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/friends/")
public class FriendController {
    private final FriendService friendService;
    private final Profile profile;

//    @PostMapping("/add/{id}")
//    public void addFriend(@PathVariable("id") Long idTarget){
//        friendService.addFriend(profile.getId(),idTarget);
//    }

    @PostMapping("/remove/{id}")
    public void removeFriend(@PathVariable("id") Long idTarget){
        friendService.removeFriend(profile.getId(),idTarget);
    }
//    @GetMapping("/getall/{id}")
//    public List<FriendResponse> getAllFriend(@PathVariable("id") Long id){
//        return friendService.getAll(id);
//    }

//    @GetMapping("/getall/me")
//    public List<FriendResponse>getAllMyFriend(){
//        return friendService.getAll(profile.getId());
//    }

    @PostMapping("/getall/{id}")
    public Page<FriendResponse> getAllFriend(@PathVariable("id") Long id, @RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return friendService.getAllByUserId(id,pageRequest);
    }
    @PostMapping("/getall/me")
    public Page<FriendResponse>getAllMyFriend(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return friendService.getAllByUserId(profile.getId(),pageRequest);
    }

    @PostMapping("/friend-suggestion")
    public List<UserInfoResponse> getListFriendSuggestion(){
        return friendService.listFriendSuggestions(profile.getId());
    }
}
