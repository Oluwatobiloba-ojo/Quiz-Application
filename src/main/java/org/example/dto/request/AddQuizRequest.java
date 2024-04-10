package org.example.dto.request;

import lombok.Data;
import org.example.data.model.QuizQuestion;

import java.util.List;

@Data
public class AddQuizRequest {
    private String titleQuiz ;
    private String description ;
    private String userEmail ;
    private List<QuizQuestion> questionList ;
}
