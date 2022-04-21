package com.viuniteam.socialviuni.dto.request.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensiveKeyword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostSaveRequest {

    @NotEmpty(message = "Nội dung bài viết không được để trống")
    @Length(min = 1, max = 5000, message = "Nội dung phải có độ dài từ 1 đến 5000 kí tự")
//    @ValidOffensive(name="Nội dung bài viết")
    @ValidOffensiveKeyword
    private String content;

    @NotNull(message = "Quyền riêng tư bài viết không được để trống")
    @Min(value = 1,message = "Quyền riêng tư phải có độ lớn từ 1 đến 3")
    @Max(value = 3,message = "Quyền riêng tư phải có độ lớn từ 1 đến 3")
    private Integer privacy;

    @JsonProperty("images")
    private List<Long> imageIds = new ArrayList<>();
}
