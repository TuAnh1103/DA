package com.viuniteam.socialviuni.dto.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data

public class UserRecoveryPasswordRequest {

    @NotEmpty(message = "Tên người dùng không được để trống")
    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @Length(min = 8, max = 30, message = "Mật khẩu phải có độ dài từ 8 đến 30")
    private String password;

    @Length(min = 8, max = 8, message = "Mã xác nhận không chính xác")
    private String code;
}
