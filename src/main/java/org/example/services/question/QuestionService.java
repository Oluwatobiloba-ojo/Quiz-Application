package org.example.services.question;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;

import java.util.List;

public interface QuestionService {

    void add(Question question, QuizPage page, Long count);
    Question update(Long questionId, QuizPage page, Question newQuestion);
    void deleteQuestion(Long questionNo, QuizPage page);
    void addQuestion(Question question, QuizPage page);
    List<Question> findQuestionsFor(QuizPage page);
    void deleteQuestions(QuizPage page);
}
