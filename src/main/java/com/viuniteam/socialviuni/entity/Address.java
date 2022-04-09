package com.viuniteam.socialviuni.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(100)")
    private String name;


    @OneToOne(mappedBy = "homeTown")
    private User userHomeTown;

    @OneToOne(mappedBy = "currentCity")
    private User userCurrentCity;
}
