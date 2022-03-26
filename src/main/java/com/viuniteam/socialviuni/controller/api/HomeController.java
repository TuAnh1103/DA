package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.service.RequestService;
import com.viuniteam.socialviuni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;
//    @GetMapping("/home")
    public String home(HttpServletRequest request){
        PageRequest pageRequest = PageRequest.of(0,100);
        Page<UserInfoResponse> userInfoResponsePage = userService.findAll(pageRequest);
        List<UserInfoResponse> userInfoResponseList = userInfoResponsePage.toList();
        request.setAttribute("users",userInfoResponseList);
        return "index";
    }
    @GetMapping("/home")
    public List<UserInfoResponse> index(HttpServletRequest request){
//        System.out.println(requestService.getClientIp(request));
        PageRequest pageRequest = PageRequest.of(0,100);
        Page<UserInfoResponse> userInfoResponsePage = userService.findAll(pageRequest);
        List<UserInfoResponse> userInfoResponseList = userInfoResponsePage.toList();
        return userInfoResponseList;
    }

}
