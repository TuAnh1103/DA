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

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="user_id",updatable  = false)
    private User author;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column
    private Integer privicy; /* 1-cong khai 2-ban be 3- chi minh toi*/


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL) // cascadetype.all la xoa post thi all cmt cx bi xoa nhe
    private List<Comment> comments=new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "post_images",joinColumns = @JoinColumn(name = "post_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Share> shares = new ArrayList<>();

}
