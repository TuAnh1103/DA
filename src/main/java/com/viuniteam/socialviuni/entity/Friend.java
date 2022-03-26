package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Friend extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}