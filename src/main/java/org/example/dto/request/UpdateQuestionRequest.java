package org.example.dto.request;

import lombok.Data;
import org.example.data.model.QuizQuestion;

@Data
public class UpdateQuestionRequest {
    private String email;
    private String title;
    private Long questionId;
    private QuizQuestion newQuestion;
}
