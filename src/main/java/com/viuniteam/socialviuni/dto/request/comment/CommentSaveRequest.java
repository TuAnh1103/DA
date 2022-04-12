package com.viuniteam.socialviuni.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensive;
import com.viuniteam.socialviuni.annotation.offensivekeyword.ValidOffensiveKeyword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentSaveRequest {

    @NotEmpty(message = "Comment không được để trống")
    @Length(min = 1, max = 500, message = "Comment phải có độ dài từ 1 đến 500 kí tự")
//    @ValidOffensive(name = "Comment")
    @ValidOffensiveKeyword
    private String content;

    @JsonProperty("image_id")
    private Long imageId;

}
