package org.example.services.page;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.User;

import java.util.List;

public interface PageService {
    QuizPage add(String titleQuiz, List<Question> questionList, String description, User user);
    void updateQuestion(Long questionId, String title, User user, Question newQuestion);
    QuizPage findPageByTitle(String quizTitle, List<QuizPage> quizPageList);
    List<Question> readQuestion(String title, List<QuizPage> quizPageList);
    void deleteQuestion(String quizTitle, List<QuizPage> quizPageList, Long questionNo);
    void addQuestion(Question question, String quizTitle, List<QuizPage> quizPageList);
}
