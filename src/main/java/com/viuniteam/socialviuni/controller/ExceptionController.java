package com.viuniteam.socialviuni.controller;

import com.viuniteam.socialviuni.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<APIError> objectNotFoundException(ObjectNotFoundException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<APIError> badRequestException(BadRequestException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OKException.class)
    public final ResponseEntity<APIError> OKException(OKException ex) {
        APIError apiError = new APIError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.OK);
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
