package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.response.notification.NotificationResponse;
import com.viuniteam.socialviuni.service.NotificationService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    private final Profile profile;

    @PostMapping
    public Page<NotificationResponse> getAll(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return notificationService.getAll(userService.findOneById(profile.getId()), pageRequest);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        notificationService.delete(id);
    }
    @PostMapping("/seen")
    public void seenAllNotification(){
        notificationService.seenNotification();
    }
    @PostMapping("/read/{id}")
    public void readNotification(@PathVariable("id") Long id){
        notificationService.readNotification(id);
    }
}
