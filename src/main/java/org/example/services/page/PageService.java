package org.example.services.page;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.QuizQuestion;
import org.example.data.model.User;

import java.util.List;

public interface PageService {
    void add(String titleQuiz, List<Question> questionList, String description, User user);
    void updateQuestion(Long questionId, String title, User user, Question newQuestion);
    QuizPage findPageByTitleAndUser(String quizTitle, User user);
    List<Question> readQuestion(String title, User user);
    void deleteQuestion(String quizTitle, User user, Long questionNo);
    void addQuestion(Question question, String quizTitle, User user);
    List<QuizPage> viewAllPage();
    void deletePage(User user, String quizTitle);
    List<QuizQuestion> getQuestionsOf(String quizTitle);
}
