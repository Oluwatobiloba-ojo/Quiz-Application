package org.example.dto.request;

import lombok.Data;
import org.example.data.model.QuizQuestion;

@Data
public class AddQuestionRequest {
    private String quizTitle ;
    private String userEmail ;
    private QuizQuestion question ;
}
