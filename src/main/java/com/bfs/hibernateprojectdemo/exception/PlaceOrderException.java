package com.bfs.hibernateprojectdemo.exception;

public class PlaceOrderException extends RuntimeException{
    public PlaceOrderException(String msg){
        super(msg);
    }
}
