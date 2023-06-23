package com.bfs.hibernateprojectdemo.exception;

public class UserExistedException extends RuntimeException{
    public UserExistedException(String msg){
        super(msg);
    }
}
