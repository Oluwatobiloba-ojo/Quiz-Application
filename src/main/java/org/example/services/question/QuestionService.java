package org.example.services.question;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;

import java.util.List;

public interface QuestionService {

    Question add(Question question, QuizPage page, Long count);
    void update(Long questionId, List<Question> questionList, Question newQuestion);
    void deleteQuestion(Long questionNo, List<Question> questionList);
    Question addQuestion(List<Question> questionList, Question question, QuizPage page);
}
