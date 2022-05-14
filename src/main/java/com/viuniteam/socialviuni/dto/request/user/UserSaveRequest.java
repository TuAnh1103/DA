package com.viuniteam.socialviuni.dto.request.user;

import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensive;
import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensiveKeyword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
@Data
public class UserSaveRequest {
    @NotEmpty(message = "Tên người dùng không được để trống")
    @NotBlank(message = "Tên người dùng không được để trống")
    @Length(min = 5, max = 15, message = "Tên người dùng phải có độ dài từ 5 đến 15")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "Tên người dùng không được chứa kí tự cấm")
//    @ValidOffensive(name = "Tên người dùng")
    @ValidOffensiveKeyword
    private String username;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(min = 8, max = 30, message = "Mật khẩu phải có độ dài từ 8 đến 30")
    private String password;


    @NotEmpty(message = "Email không được để trống")
    @NotBlank(message = "Email không được để trống")
    @Length(min = 10, max = 50, message = "Email phải có độ dài từ 10 đến 50")
    @Email(message = "Email không đúng định dạng",
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;


    @NotEmpty(message = "Họ tên đệm không được để trống")
    @NotBlank(message = "Họ tên đệm không được để trống")
    @Length(min = 1, max = 25, message = "Họ tên đệm phải có độ dài từ 1 đến 25")
    @Pattern(regexp = "^[a-zA-Z ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+$", message = "Họ tên đệm không được chứa kí tự cấm")
//    @ValidOffensive(name = "Họ tên đệm")
    @ValidOffensiveKeyword
    private String lastName;

    @NotEmpty(message = "Tên không được để trống")
    @NotBlank(message = "Tên không được để trống")
    @Length(min = 1, max = 15, message = "Tên phải có độ dài từ 1 đến 15")
    @Pattern(regexp = "^[a-zA-Z ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+$", message = "Tên không được chứa kí tự cấm")
//    @ValidOffensive(name = "Tên")
    @ValidOffensiveKeyword
    private String firstName;

    @DateTimeFormat(pattern="yyyyMMdd")
    private String dob;

    private boolean gender;

    @Length(min = 8, max = 8, message = "Mã xác nhận không chính xác")
    private String code;

}
