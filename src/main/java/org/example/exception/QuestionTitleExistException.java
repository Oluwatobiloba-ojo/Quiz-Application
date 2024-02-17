package org.example.exception;

public class QuestionTitleExistException extends QuizAppException {
    public QuestionTitleExistException(String message){
        super(message);
    }
}
