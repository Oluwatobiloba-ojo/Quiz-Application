package org.example.exception;

public class InvalidLoginDetail extends RuntimeException{
    public InvalidLoginDetail(String message){
        super(message);
    }
}
