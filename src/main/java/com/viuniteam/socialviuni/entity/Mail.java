package com.viuniteam.socialviuni.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail extends BaseEntity{
    @Column(unique = true)
    private String email;
    @Column
    private String code;
}
