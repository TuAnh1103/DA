package com.viuniteam.socialviuni.entity;

import com.viuniteam.socialviuni.enumtype.NotificationPostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private NotificationPostType notificationPostType;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Notification notification;
}
