package org.example.exception;

public class ActionDoneException extends RuntimeException {
    public ActionDoneException(String message){
        super(message);
    }
}
