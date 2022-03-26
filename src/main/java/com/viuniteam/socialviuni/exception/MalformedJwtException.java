package com.viuniteam.socialviuni.exception;

public class MalformedJwtException extends RuntimeException{
    public MalformedJwtException(String message) {
        super(message);
    }
}
