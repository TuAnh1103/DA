package com.viuniteam.socialviuni.entity;

import com.viuniteam.socialviuni.enumtype.NotificationSeenType;
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

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private NotificationSeenType status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private NotificationPost notificationPost;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private NotificationFollow notificationFollow;
}
