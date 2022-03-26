package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.user.UserChangeEmailRequest;
import com.viuniteam.socialviuni.dto.request.user.UserChangePasswordRequest;
import org.springframework.http.ResponseEntity;

public interface SettingService {
    ResponseEntity<?> changePassword(UserChangePasswordRequest userChangePasswordRequest);
    ResponseEntity<?> changeEmail(UserChangeEmailRequest userChangeEmailRequest);
}
