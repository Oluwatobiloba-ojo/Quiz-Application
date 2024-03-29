package org.example.data.model;

import lombok.Data;

@Data
public class QuizQuestion {
    private String title;
    private String question;
    private String answer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
}
