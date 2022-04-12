package com.viuniteam.socialviuni.dto.request.share;

import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensive;
import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensiveKeyword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ShareSaveRequest {
    @NotEmpty(message = "Nội dung bài viết không được để trống")
    @Length(min = 1, max = 5000, message = "Nội dung phải có độ dài từ 1 đến 5000 kí tự")
//    @ValidOffensive(name = "Nội dung bài viết")
    @ValidOffensiveKeyword
    private String content;
}
