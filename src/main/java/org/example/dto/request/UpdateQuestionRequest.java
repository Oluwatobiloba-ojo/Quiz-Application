package org.example.dto.request;

import lombok.Data;
import org.example.data.model.Question;
@Data
public class UpdateQuestionRequest {
    private String email;
    private String title;
    private Long questionId;
    private Question newQuestion;
}
