package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MemberGroupMessage extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToMany(mappedBy = "memberGroupMessageList")
//    private List<GroupMessage> groupMessageList;

    @ManyToOne
    @JoinColumn(name = "group_message_id")
    private GroupMessage groupMessage;

    private boolean admin;

    @OneToMany(mappedBy = "member")
    private List<ChatGroupMessage> chatGroupMessageList = new ArrayList<>();

}
