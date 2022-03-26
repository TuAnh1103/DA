package com.viuniteam.socialviuni.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonException{
    private int status;
    private String message;
}