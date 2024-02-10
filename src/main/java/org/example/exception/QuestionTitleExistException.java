package org.example.exception;

public class QuestionTitleExistException extends RuntimeException {
    public QuestionTitleExistException(String message){
        super(message);
    }
}
