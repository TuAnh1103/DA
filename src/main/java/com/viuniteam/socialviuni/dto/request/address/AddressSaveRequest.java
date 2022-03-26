package com.viuniteam.socialviuni.dto.request.address;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressSaveRequest {
    @NotNull(message = "Địa chỉ không được để trống")
    @NotBlank(message = "Địa chỉ không được để trống")
    @Length(min = 5,max = 100,  message = "Địa chỉ phải có độ dài từ 5 đến 100")
    private String name;
}
