package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Image extends BaseEntity{
    private String linkImage;

    @ManyToMany(mappedBy = "avatarImage")
    private List<User> userAvatar = new ArrayList<>();

    @ManyToMany(mappedBy = "coverImage")
    private List<User> userCover = new ArrayList<>();

    @ManyToMany(mappedBy = "images")
    private List<Post> post = new ArrayList<>();

    @ManyToMany(mappedBy = "images")
    private List<Comment> comment;

    @ManyToMany (mappedBy = "image")
    private List<Message> message;



    @ManyToMany (mappedBy = "avatar")
    private List<GroupMessage> groupMessage;


    @ManyToMany(mappedBy = "image")
    private List<ChatGroupMessage> chatGroupMessage;


}
