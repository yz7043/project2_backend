package com.bfs.hibernateprojectdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class BeanUtilsException extends RuntimeException{
    private Object errorData;

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }


    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    @NonNull
    public BeanUtilsException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
