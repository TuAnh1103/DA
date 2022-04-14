package com.viuniteam.socialviuni.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseEntity{
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    private NotificationPost notificationPost;

    @OneToOne(cascade = CascadeType.ALL)
    private NotificationFollow notificationFollow;
}
