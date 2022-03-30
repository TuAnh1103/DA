package com.viuniteam.socialviuni.dto.request.browser;

import com.viuniteam.socialviuni.dto.BaseDTO;
import lombok.Data;

@Data
public class BrowserSaveRequest extends BaseDTO {
    private String ip;
    private String browser;
}
