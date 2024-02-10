package org.example.services.user;

import org.example.data.model.Question;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message){
        super(message);
    }
}
