package com.viuniteam.socialviuni.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
public class TestController {
    @GetMapping("/hello")
    public String a(){
        return "hello";
    }
}
