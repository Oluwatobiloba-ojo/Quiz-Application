package org.example.exception;

public class QuestionNotFoundException extends QuizAppException {
    public QuestionNotFoundException(String message){
        super(message);
    }
}
