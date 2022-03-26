package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Message extends BaseEntity{

    private String content;
    @ManyToOne
    @JoinColumn(name = "user_source_id")
    private User userSource;
    @ManyToOne
    @JoinColumn(name = "user_target_id")
    private User userTarget;

    @ManyToMany
    @JoinTable(name = "message_images",joinColumns = @JoinColumn(name = "message_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> image = new ArrayList<>();


}
