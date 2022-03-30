package com.viuniteam.socialviuni.controller;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.dto.request.user.UserRecoveryPasswordRequest;
import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.security.JwtTokenUtil;
import com.viuniteam.socialviuni.security.jwt.JwtRequest;
import com.viuniteam.socialviuni.security.jwt.JwtResponse;
import com.viuniteam.socialviuni.service.BrowserService;
import com.viuniteam.socialviuni.service.MailService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserSaveRequest userSaveRequest){
        return userService.register(userSaveRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PostMapping("/recovery")
    public ResponseEntity<?> recoveryPassword(@Valid @RequestBody UserRecoveryPasswordRequest userRecoveryPasswordRequest){
        return userService.recoveryPassword(userRecoveryPasswordRequest);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
