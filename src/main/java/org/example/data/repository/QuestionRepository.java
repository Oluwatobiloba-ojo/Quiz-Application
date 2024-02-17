package org.example.data.repository;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllQuestionByQuizPage(QuizPage page);

}
