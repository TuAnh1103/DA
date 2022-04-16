package com.viuniteam.socialviuni.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @UpdateTimestamp
    private Date createdDate;

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
