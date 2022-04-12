package com.viuniteam.socialviuni.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensiveKeyword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserUpdateInfoRequest {
//    @NotEmpty(message = "Họ tên đệm không được để trống")
//    @NotBlank(message = "Họ tên đệm không được để trống")
    @Length(min = 1, max = 25, message = "Họ tên đệm phải có độ dài từ 1 đến 25")
    @Pattern(regexp = "^[a-zA-Z ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+$", message = "Họ tên đệm không được chứa kí tự cấm")
    @JsonProperty("last_name")
    @ValidOffensiveKeyword
    private String lastName;

//    @NotEmpty(message = "Tên không được để trống")
//    @NotBlank(message = "Tên không được để trống")
    @Length(min = 1, max = 15, message = "Tên phải có độ dài từ 1 đến 15")
    @Pattern(regexp = "^[a-zA-Z ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+$", message = "Tên không được chứa kí tự cấm")
    @JsonProperty("first_name")
    @ValidOffensiveKeyword
    private String firstName;

    @DateTimeFormat(pattern="yyyyMMdd")
    private String dob;

    @Pattern(regexp = "^[a-zA-Z0-9 ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴắằẳẵặăấầẩẫậâáàãảạđếềểễệêéèẻẽẹíìỉĩịốồổỗộôớờởỡợơóòõỏọứừửữựưúùủũụýỳỷỹỵ]+$", message = "Giới thiệu bản thân không được chứa kí tự cấm")
    @Length(min = 1, max = 160, message = "Giới thiệu bản thân phải có độ dài từ 1 đến 160")
    @ValidOffensiveKeyword
    private String bio;

    private String gender;

    @JsonProperty("hometown")
    private Long idHomeTown;

    @JsonProperty("current_city")
    private Long idCurrentCity;

    @JsonProperty("avatar_image")
    private Long idAvatarImage;

    @JsonProperty("cover_image")
    private Long idCoverImage;

}
