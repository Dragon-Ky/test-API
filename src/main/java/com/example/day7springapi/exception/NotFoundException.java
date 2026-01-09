package com.example.day7springapi.exception;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
