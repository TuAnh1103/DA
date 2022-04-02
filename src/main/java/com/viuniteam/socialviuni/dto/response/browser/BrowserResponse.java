package com.viuniteam.socialviuni.dto.response.browser;

import com.viuniteam.socialviuni.dto.BaseDTO;
import lombok.Data;

@Data
public class BrowserResponse extends BaseDTO {
    private String ip;
    private String browser;
}
