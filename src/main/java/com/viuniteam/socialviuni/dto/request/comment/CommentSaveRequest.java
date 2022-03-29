package com.viuniteam.socialviuni.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentSaveRequest {

    @NotEmpty(message = "Nội dung bình luận không được để trống")
    @Length(min = 1, max = 500, message = "Nội dung bình luận phải có độ dài từ 1 đến 500 kí tự")
    private String content;


    @JsonProperty("image_id")
    private Long imageId;

}
