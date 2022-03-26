package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.List;

@Entity
@Data
public class ChatGroupMessage extends BaseEntity{
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberGroupMessage member;

    @ManyToOne
    @JoinColumn(name = "group_message_id")
    private GroupMessage groupMessage;

    @ManyToMany
    @JoinTable(name = "chat_group_message_images",joinColumns = @JoinColumn(name = "chat_group_message_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> image;

}
