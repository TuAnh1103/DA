package com.viuniteam.socialviuni.controller;

import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.exception.MalformedJwtException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> objectNotFoundException(){
        return new ResponseEntity<>(new JsonException(404, "Tài khoản không tồn tại"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> dateTimeParseException(){
        return new ResponseEntity<>(new JsonException(400, "Định dạng thời gian không hợp lệ"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> malformedJwtException(){
        return new ResponseEntity<>(new JsonException(400, "Xác thực danh tính không thành công"), HttpStatus.BAD_REQUEST);
    }

}
