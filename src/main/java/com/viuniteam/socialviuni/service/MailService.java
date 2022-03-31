package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.enumtype.SendCodeType;
import org.springframework.http.ResponseEntity;

public interface MailService {
    void sendCode(String email, String username, SendCodeType sendCodeType);
    void deleteByEmail(String email);
    boolean hasCode(String email, String code);
}
