package com.viuniteam.socialviuni.dto.request.user;

import com.viuniteam.socialviuni.dto.request.BaseFilter;
import lombok.Data;

@Data
public class UserFilterRequest extends BaseFilter {
    private String username;
    private String keyword;
}
