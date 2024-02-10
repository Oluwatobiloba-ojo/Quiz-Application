package org.example.data.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "page")
public class QuizPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date = LocalDate.now();

    private String description ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizPage", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Question> questionList ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private User user;
}
