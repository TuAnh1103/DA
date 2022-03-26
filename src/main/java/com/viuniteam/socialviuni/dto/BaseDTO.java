package com.viuniteam.socialviuni.dto;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Data
public class BaseDTO {
    private Long id;
    private LocalDate createdDate;
}
