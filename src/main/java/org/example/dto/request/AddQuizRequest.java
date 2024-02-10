package org.example.dto.request;

import lombok.Data;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.example.data.model.Question;

import java.util.List;

@Data
public class AddQuizRequest {
    private String titleQuiz ;
    private String description ;
    private String userEmail ;
    private List<Question> questionList ;
}
