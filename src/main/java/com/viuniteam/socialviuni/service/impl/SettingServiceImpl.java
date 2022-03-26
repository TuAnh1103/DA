package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserChangeEmailRequest;
import com.viuniteam.socialviuni.dto.request.user.UserChangePasswordRequest;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.SendCodeType;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.service.MailService;
import com.viuniteam.socialviuni.service.SettingService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Profile profile;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    @Override
    public ResponseEntity<?> changePassword(UserChangePasswordRequest userChangePasswordRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getUsername(), userChangePasswordRequest.getOldPassword()));
        }
        catch (Exception e){
            return new ResponseEntity<>(new JsonException(400,"Mật khẩu cũ không chính xác"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOneById(profile.getId());
        user.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
        userService.update(user);
        return new ResponseEntity<>(new JsonException(200,"Thay đổi mật khẩu thành công"), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> changeEmail(UserChangeEmailRequest userChangeEmailRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getUsername(), userChangeEmailRequest.getPassword()));
        }
        catch (Exception e){
            return new ResponseEntity<>(new JsonException(400,"Mật khẩu không chính xác"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOneById(profile.getId());
        if(user.getEmail().equals(userChangeEmailRequest.getEmail()))
            return new ResponseEntity<>(new JsonException(400,"Email mới không được trùng với email cũ"),HttpStatus.BAD_REQUEST);

        Optional<User> checkEmail = userService.findByEmail(userChangeEmailRequest.getEmail());
        if(!checkEmail.isEmpty())
            return new ResponseEntity<>(new JsonException(400,"Email đã tồn tại"),HttpStatus.BAD_REQUEST);

        if(userChangeEmailRequest.getCode() != null){ // gửi kèm code
            if(mailService.hasCode(userChangeEmailRequest.getEmail(),userChangeEmailRequest.getCode())){
                mailService.deleteByEmail(userChangeEmailRequest.getEmail());
                user.setEmail(userChangeEmailRequest.getEmail());
                userService.update(user);
                return new ResponseEntity<>(new JsonException(200,"Thay đổi email thành công"), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(new JsonException(400,"Mã xác nhận không chính xác"), HttpStatus.BAD_REQUEST);
        }
        return mailService.sendCode(userChangeEmailRequest.getEmail(), user.getUsername(), SendCodeType.CHANGEEMAIL);
    }
}
