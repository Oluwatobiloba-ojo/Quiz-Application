package org.example.services.question;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.QuizQuestion;

import java.util.List;

public interface QuestionService {

    void add(QuizQuestion question, QuizPage page, Long count);
    Question update(Long questionId, QuizPage page, QuizQuestion newQuestion);
    void deleteQuestion(Long questionNo, QuizPage page);
    void addQuestion(QuizQuestion question, QuizPage page);
    List<QuizQuestion> findQuestionsFor(QuizPage page);
    void deleteQuestions(QuizPage page);
}
