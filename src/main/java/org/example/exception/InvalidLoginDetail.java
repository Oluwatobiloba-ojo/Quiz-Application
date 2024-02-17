package org.example.exception;

public class InvalidLoginDetail extends QuizAppException{
    public InvalidLoginDetail(String message){
        super(message);
    }
}
