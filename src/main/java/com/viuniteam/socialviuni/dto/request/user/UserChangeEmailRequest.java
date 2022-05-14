package com.viuniteam.socialviuni.dto.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class UserChangeEmailRequest {
    @NotEmpty(message = "Mật khẩu được để trống")
    @NotBlank(message = "Mật khẩu không được để trống")
    @NotNull(message = "Mật khẩu không được để trống")
    private String password;

    @NotEmpty(message = "Email mới không được để trống")
    @NotBlank(message = "Email mới không được để trống")
    @Length(min = 10, max = 50, message = "Email phải có độ dài từ 10 đến 50")
    @Email(message = "Email không đúng định dạng",
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @Length(min = 8, max = 8, message = "Mã xác nhận không chính xác")
    private String code;
}
