package org.example.exception;

public class AnswerNotFoundException extends QuizAppException {
    public AnswerNotFoundException(String message){
        super(message);
    }
}
