package org.example.services.page;

import org.example.data.model.QuizPage;
import org.example.data.model.QuizQuestion;
import org.example.data.model.User;
import org.example.dto.response.QuizPageResponse;

import java.util.List;

public interface PageService {
    void add(String titleQuiz, List<QuizQuestion> questionList, String description, User user);
    void updateQuestion(Long questionId, String title, User user, QuizQuestion newQuestion);
    QuizPage findPageByTitleAndUser(String quizTitle, User user);
    List<QuizQuestion> readQuestion(String title, User user);
    void deleteQuestion(String quizTitle, User user, Long questionNo);
    void addQuestion(QuizQuestion question, String quizTitle, User user);
    List<QuizPageResponse> viewAllPage();
    void deletePage(User user, String quizTitle);
    List<QuizQuestion> getQuestionsOf(String quizTitle);
    List<QuizPageResponse> findPagesBelongingTo(User user);
}
