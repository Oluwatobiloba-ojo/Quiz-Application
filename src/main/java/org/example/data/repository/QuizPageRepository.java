package org.example.data.repository;

import org.example.data.model.QuizPage;
import org.example.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizPageRepository extends JpaRepository<QuizPage, Long> {
    QuizPage findQuizPageByTitle(String titleQuiz);
    Optional<QuizPage> findQuizPageByUser_AndTitle(User user, String title);
}
