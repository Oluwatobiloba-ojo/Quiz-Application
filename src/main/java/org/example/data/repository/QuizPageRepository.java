package org.example.data.repository;

import org.example.data.model.QuizPage;
import org.example.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizPageRepository extends JpaRepository<QuizPage, Long> {
    QuizPage findQuizPageByTitle(String titleQuiz);

    QuizPage findQuizPageByUser_AndTitle(User user, String title);
}
