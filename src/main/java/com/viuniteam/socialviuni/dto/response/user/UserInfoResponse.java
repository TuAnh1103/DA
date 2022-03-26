package com.viuniteam.socialviuni.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserInfoResponse {

    private Long id;

    private String username;

    //private String password;

    //private String email;

//    private boolean active;

    private boolean gender;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("first_name")
    private String firstName;

    private LocalDate dob;

    private String bio;

    @JsonProperty("created_date")
    private Date createdDate;


    @JsonProperty("avatar_image")
    private ImageResponse avatar;

    @JsonProperty("cover_image")
    private ImageResponse cover;
}
