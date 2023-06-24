package com.bfs.hibernateprojectdemo.exception;

public class OrderStatusTransferException extends RuntimeException{
    public OrderStatusTransferException(String msg){
        super(msg);
    }
}
