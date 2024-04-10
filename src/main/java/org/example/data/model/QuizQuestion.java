package org.example.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuizQuestion {
    private String question;
    private String answer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public QuizQuestion (Question question){
        this.answer = question.getAnswer();
        this.optionA = question.getOptionA();
        this.optionB = question.getOptionB();
        this.optionC = question.getOptionC();
        this.optionD = question.getOptionD();
        this.question = question.getQuestion();
    }
}
