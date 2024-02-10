package org.example.exception;

public class QuizAppExistException extends RuntimeException {
    public QuizAppExistException(String message){
        super(message);
    }
}
