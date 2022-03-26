package com.viuniteam.socialviuni.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile{
    private Long id;
    private String username;
    private String email;
    private boolean active;
    private boolean gender;
    private String lastName;
    private String firstName;
    private LocalDate dob;
    private List<String> roles = new ArrayList<>();
    private Date createdDate;
}
