package com.viuniteam.socialviuni.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtRequest {
    private String username;
    private String password;
}
