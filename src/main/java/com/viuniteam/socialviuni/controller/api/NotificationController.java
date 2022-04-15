package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.notification.NotificationPostResponse;
import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/noti")
    public void add(){
//        notificationService.createNotification();
    }

    @GetMapping
    public List<NotificationResponse> getAll(){
        return notificationService.getAll();
    }
}
