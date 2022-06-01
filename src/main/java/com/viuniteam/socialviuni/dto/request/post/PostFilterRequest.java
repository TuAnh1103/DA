package com.viuniteam.socialviuni.dto.request.post;

import com.viuniteam.socialviuni.dto.request.BaseFilter;
import lombok.Data;

@Data
public class PostFilterRequest extends BaseFilter {
    private String keyword;
}
