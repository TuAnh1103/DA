package com.viuniteam.socialviuni.dto.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserChangePasswordRequest {
    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    @NotBlank(message = "Mật khẩu cũ không được để trống")
    @NotNull(message = "Mật khẩu cũ không được để trống")
//    @Length(min = 8, max = 30, message = "Mật khẩu cũ phải có độ dài từ 8 đến 30")
    private String oldPassword;

    @NotEmpty(message = "Mật khẩu mới không được để trống")
    @NotBlank(message = "Mật khẩu mới không được để trống")
    @NotNull(message = "Mật khẩu mới không được để trống")
    @Length(min = 8, max = 30, message = "Mật khẩu mới phải có độ dài từ 8 đến 30")
    private String newPassword;
}
