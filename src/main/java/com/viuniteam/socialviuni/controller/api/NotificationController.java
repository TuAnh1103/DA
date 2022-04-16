package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.service.NotificationService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    private final Profile profile;

    @GetMapping
    public List<NotificationResponse> getAll(){
        return notificationService.getAll(userService.findOneById(profile.getId()));
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        notificationService.delete(id);
    }
}
