package org.example.exception;

public class UserExistException extends QuizAppException {
    public UserExistException(String message){
        super(message);
    }
}
