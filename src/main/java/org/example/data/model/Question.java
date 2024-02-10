package org.example.data.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_quizPage_id", referencedColumnName = "id")
    private QuizPage quizPage ;
}
