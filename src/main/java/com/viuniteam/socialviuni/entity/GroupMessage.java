package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class GroupMessage extends BaseEntity{
    private String name;

    @ManyToMany
    @JoinTable(name = "group_message_avatar",joinColumns = @JoinColumn(name = "group_message_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> avatar;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

//    @ManyToMany
//    @JoinTable(name = "member_group_message_list", joinColumns = @JoinColumn(name = "group_id",nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "member_id",nullable = false))
//    private List<MemberGroupMessage> memberGroupMessageList = new ArrayList<>();
//
    @OneToMany(mappedBy = "groupMessage")
    private List<MemberGroupMessage> memberGroupMessageList = new ArrayList<>();


    @OneToMany(mappedBy = "groupMessage")
    private List<ChatGroupMessage> chatGroupMessageList = new ArrayList<>();
}
