package org.example.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "question")
@ToString(exclude = "quizPage")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String question ;
    private String optionA ;
    private String optionB ;
    private String optionC ;
    private String optionD ;
    private String answer ;
    private Long questionNo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_quizPage_id")
    private QuizPage quizPage ;
}
