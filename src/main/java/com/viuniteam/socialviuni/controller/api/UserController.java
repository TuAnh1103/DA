package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserUpdateInfoRequest;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;
    private final Profile profile;
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> findById(){
        return new ResponseEntity<>(userService.findById(profile.getId()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserInfoResponse>> findAll(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        Page<UserInfoResponse> userInfoResponsePage = userService.findAll(pageRequest);
        return ResponseEntity.ok(userInfoResponsePage);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInfo(@Valid @RequestBody UserUpdateInfoRequest userUpdateInfoRequest){
        return userService.updateInfo(userUpdateInfoRequest);
    }

}
