package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/noti")
    public void add(){
//        notificationService.createNotification();
    }

}
