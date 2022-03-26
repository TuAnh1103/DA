package com.viuniteam.socialviuni.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Comment extends BaseEntity{

    @Column(columnDefinition = "nvarchar(500)")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToMany
    @JoinTable(name = "comment_images",joinColumns = @JoinColumn(name = "comment_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> images = new ArrayList<>();
}
