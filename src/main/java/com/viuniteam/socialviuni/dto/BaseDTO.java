package com.viuniteam.socialviuni.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseDTO {
    private Long id;

    @JsonProperty("created_date")
    private Date createdDate;
}
