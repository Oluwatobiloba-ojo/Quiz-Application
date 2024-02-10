package org.example.data.repository;

import org.example.data.model.QuizPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizPageRepository extends JpaRepository<QuizPage, Long> {
    QuizPage findQuizPageByTitle(String titleQuiz);
}
