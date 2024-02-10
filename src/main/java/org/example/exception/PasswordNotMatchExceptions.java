package org.example.exception;

public class PasswordNotMatchExceptions extends RuntimeException{
    public PasswordNotMatchExceptions(String message){
        super(message);
    }
}
