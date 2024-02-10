package org.example.services.question;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.repository.QuestionRepository;
import org.example.exception.AnswerNotFoundException;
import org.example.exception.QuestionBlankException;
import org.example.exception.QuestionNotFoundException;
import org.example.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionRepository questionRepository;
    @Override
    public Question add(Question question, QuizPage page, Long count) {
        validateQuestion(question);
        question.setQuizPage(page);
        question.setQuestionNo(count);
        return questionRepository.save(question);
    }

    private void validateQuestion(Question question) {
        if (question.getQuestion() == null) throw new QuestionBlankException("Question is empty");
        if (question.getAnswer() == null) throw new QuestionBlankException("Answer is empty");
        if (question.getOptionA() == null) throw new QuestionBlankException("Options is not valid");
        if (question.getOptionB() == null) throw new QuestionBlankException("Options is not valid");
        if (question.getOptionC() == null) throw new QuestionBlankException("Options is not valid");
        if (question.getOptionD() == null) throw new QuestionBlankException("Options is not valid");
        if (!answerNotExistInOption(question)) throw new AnswerNotFoundException("Answer does not exist in option");
    }

    @Override
    public void update(Long questionId, List<Question> questionList, Question newQuestion) {
        for (int count = 0; count < questionList.size(); count++){
            if (questionList.get(count).getQuestionNo().equals(questionId)){
                Question oldQuestion = questionList.get(count);
                if (newQuestion.getQuestion() != null) oldQuestion.setQuestion(newQuestion.getQuestion());
                if (newQuestion.getOptionA() != null) oldQuestion.setOptionA(newQuestion.getOptionA());
                if (newQuestion.getOptionB() != null) oldQuestion.setOptionB(newQuestion.getOptionB());
                if (newQuestion.getOptionC() != null) oldQuestion.setOptionC(newQuestion.getOptionC());
                if (newQuestion.getOptionD() != null) oldQuestion.setOptionD(newQuestion.getOptionD());
                if (newQuestion.getAnswer() != null) oldQuestion.setAnswer(newQuestion.getAnswer());
            }
        }
        throw new QuestionNotFoundException("question does not exist");
    }

    @Override
    public void deleteQuestion(Long questionNo, List<Question> questionList) {
        Question question = findQuestionByQuestionNo(questionList, questionNo);
        questionRepository.delete(question);
    }

    @Override
    public Question addQuestion(List<Question> questionList, Question question, QuizPage page) {
        validateQuestion(question);
        question.setQuestionNo((long) (questionList.size() + 1));
        question.setQuizPage(page);
        return questionRepository.save(question);
    }

    private Question findQuestionByQuestionNo(List<Question> questionList, Long questionNo) {
        for (Question question : questionList){
            if (question.getQuestionNo().equals(questionNo)) return question;
        }
        throw new QuestionNotFoundException("Question does not exist");
    }

    private boolean answerNotExistInOption(Question question) {
        if (question.getOptionA().equals(question.getAnswer())) return true;
        else if (question.getOptionB().equals(question.getAnswer())) return true;
        else if (question.getOptionC().equals(question.getAnswer())) return true;
        else if (question.getOptionD().equals(question.getAnswer())) return true;
        else return false;
    }

}
