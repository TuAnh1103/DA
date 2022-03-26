package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Notification extends BaseEntity{
    private String content;

    @ManyToOne
    private User user;

    private boolean status;
}
