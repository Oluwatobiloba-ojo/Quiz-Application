package org.example.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(exclude = {"user"})
public class QuizPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date = LocalDate.now();

    private String description ;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "fk_user_id")
    @JsonIgnore
    private User user;
}
