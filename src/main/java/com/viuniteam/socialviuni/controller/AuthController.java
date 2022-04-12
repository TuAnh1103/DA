package com.viuniteam.socialviuni.controller;

import com.viuniteam.socialviuni.dto.request.browser.BrowserSaveRequest;
import com.viuniteam.socialviuni.dto.request.user.UserRecoveryPasswordRequest;
import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.security.JwtTokenUtil;
import com.viuniteam.socialviuni.security.jwt.JwtRequest;
import com.viuniteam.socialviuni.security.jwt.JwtResponse;
import com.viuniteam.socialviuni.service.BrowserService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final BrowserService browserService;
    @PostMapping("/register")
    public void register(@Valid @RequestBody UserSaveRequest userSaveRequest){
        userService.register(userSaveRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletRequest httpServletRequest) throws Exception {
        String username = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        // save browser
        if(httpServletRequest!=null){
            BrowserSaveRequest browserSaveRequest = BrowserSaveRequest.builder()
                    .ip(httpServletRequest.getRemoteAddr())
                    .browser(httpServletRequest.getHeader("User-Agent"))
                    .user(userService.findOneByUsername(username))
                    .build();
            browserService.save(browserSaveRequest);
        }
        // return token
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PostMapping("/recovery")
    public void recoveryPassword(@Valid @RequestBody UserRecoveryPasswordRequest userRecoveryPasswordRequest){
        userService.recoveryPassword(userRecoveryPasswordRequest);
    }
    private String authenticate(String username, String password) throws Exception {
        if(username.contains("@")){
            User user = userService.findByEmail(username);
            if(user == null) throw new ObjectNotFoundException("Tên tài khoản không tồn tại");
            username = user.getUsername();
        }
        else {
            User user = userService.findOneByUsername(username);
            if(user==null) throw new ObjectNotFoundException("Tên tài khoản không tồn tại");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        return username;
    }
}
