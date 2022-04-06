package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserChangeEmailRequest;
import com.viuniteam.socialviuni.dto.request.user.UserChangePasswordRequest;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.SendCodeType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.service.MailService;
import com.viuniteam.socialviuni.service.SettingService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Profile profile;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    @Override
    public void changePassword(UserChangePasswordRequest userChangePasswordRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getUsername(), userChangePasswordRequest.getOldPassword()));
        }
        catch (Exception e){
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }
        User user = userService.findOneById(profile.getId());
        user.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
        userService.update(user);
        throw new OKException("Thay đổi mật khẩu thành công");
    }

    @Override
    public void changeEmail(UserChangeEmailRequest userChangeEmailRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getUsername(), userChangeEmailRequest.getPassword()));
        }
        catch (Exception e){
            throw new BadRequestException("Mật khẩu không chính xác");
        }
        User user = userService.findOneById(profile.getId());
        if(user.getEmail().equals(userChangeEmailRequest.getEmail()))
            throw new BadRequestException("Email mới không được trùng với email cũ");

        if(userService.findByEmail(userChangeEmailRequest.getEmail())!=null)
            throw new BadRequestException("Email đã tồn tại");

        if(userChangeEmailRequest.getCode() != null){ // gửi kèm code
            if(mailService.hasCode(userChangeEmailRequest.getEmail(),userChangeEmailRequest.getCode())){
                mailService.deleteByEmail(userChangeEmailRequest.getEmail());
                user.setEmail(userChangeEmailRequest.getEmail());
                userService.update(user);
                throw new OKException("Thay đổi email thành công");
            }
            throw new BadRequestException("Mã xác nhận không chính xác");
        }
        mailService.sendCode(userChangeEmailRequest.getEmail(), user.getUsername(), SendCodeType.CHANGEEMAIL);
    }
}
