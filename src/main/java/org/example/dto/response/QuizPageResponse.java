package org.example.dto.response;

import lombok.Data;
import org.example.data.model.QuizPage;

import java.time.format.DateTimeFormatter;

@Data
public class QuizPageResponse {
    private String title;
    private String date;
    private String description;

    public QuizPageResponse(QuizPage page){
        this.description = page.getDescription();
        this.title = page.getTitle();
        this.date = page.getDate().format(DateTimeFormatter.ISO_DATE);
    }

}
