package org.example.dto.request;

import lombok.Data;
import org.example.data.model.Question;

import java.util.List;

@Data
public class AddQuestionRequest {
    private String quizTitle ;
    private String userEmail ;
    private Question question ;
}
