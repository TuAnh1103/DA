package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Report extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_source_id")
    private User userSource;

    @ManyToOne
    @JoinColumn(name = "user_target_id")
    private User userTarget;

    private String content;

    private boolean status;
}
