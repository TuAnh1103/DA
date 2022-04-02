package com.viuniteam.socialviuni.dto.request.browser;

import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrowserSaveRequest extends BaseDTO {
    private String ip;
    private String browser;
    private User user;
}
