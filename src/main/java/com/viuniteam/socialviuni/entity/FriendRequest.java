package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Data
public class FriendRequest extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
