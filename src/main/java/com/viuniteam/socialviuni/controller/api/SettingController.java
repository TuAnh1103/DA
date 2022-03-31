package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.request.user.UserChangeEmailRequest;
import com.viuniteam.socialviuni.dto.request.user.UserChangePasswordRequest;
import com.viuniteam.socialviuni.service.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/setting/")
public class SettingController {
    private final SettingService settingService;

    @PostMapping("/changepassword")
    public void changePassword(@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest){
        settingService.changePassword(userChangePasswordRequest);
    }

    @PostMapping("/changeemail")
    public void changeEmail(@Valid @RequestBody UserChangeEmailRequest userChangeEmailRequest){
        settingService.changeEmail(userChangeEmailRequest);
    }

}
