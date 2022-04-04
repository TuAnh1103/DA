package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OffensiveKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String keyword;

    public OffensiveKeyword(String keyword) {
        this.keyword = keyword;
    }

    public OffensiveKeyword() {

    }
}
