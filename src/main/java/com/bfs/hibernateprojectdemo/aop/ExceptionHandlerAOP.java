package com.bfs.hibernateprojectdemo.aop;

import com.bfs.hibernateprojectdemo.dto.error.BaseErrorResponse;
import com.bfs.hibernateprojectdemo.exception.UserExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAOP {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception e){
        return new ResponseEntity(BaseErrorResponse.builder().message("Base Exception: " + e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity handlerBadCredentialsException(BadCredentialsException e){
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(UserExistedException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
