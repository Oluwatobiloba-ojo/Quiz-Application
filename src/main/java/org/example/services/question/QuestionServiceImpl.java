package org.example.services.question;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.QuizQuestion;
import org.example.data.repository.QuestionRepository;
import org.example.exception.AnswerNotFoundException;
import org.example.exception.QuestionBlankException;
import org.example.exception.QuestionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionRepository questionRepository;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public void add(QuizQuestion question, QuizPage page, Long count) {
        validateQuestion(question);
        Question question1 = mapper.map(question, Question.class);
        question1.setQuizPage(page);
        question1.setQuestionNo(count);
        questionRepository.save(question1);
    }

    private void validateQuestion(QuizQuestion question) {
        if (question.getQuestion() == null) throw new QuestionBlankException("Question is empty");
        if (question.getAnswer() == null) throw new QuestionBlankException("Answer is empty");
        if (question.getOptionA() == null) throw new QuestionBlankException("Options is not valid");
        if (question.getOptionB() == null) throw new QuestionBlankException("Options is not valid");
        if (question.getOptionC() == null) throw new QuestionBlankException("Options is not valid");
        if (question.getOptionD() == null) throw new QuestionBlankException("Options is not valid");
        if (answerNotExistInOption(question)) throw new AnswerNotFoundException("Answer does not exist in option");
    }

    @Override
    public Question update(Long questionId, QuizPage page, QuizQuestion newQuestion) {
        List<Question> questionList = questionRepository.findAllQuestionByQuizPage(page);
        if(answerNotExistInOption(newQuestion)) throw new AnswerNotFoundException("Answer does not exist in options");
        for (int count = 0; count < questionList.size(); count++){
            if (questionList.get(count).getQuestionNo().equals(questionId)){
                Question oldQuestion = updateOldQuestion(newQuestion, questionList, count);
                questionRepository.save(oldQuestion);
                return oldQuestion;
            }
        }
        throw new QuestionNotFoundException("question does not exist");
    }

    private static Question updateOldQuestion(QuizQuestion newQuestion, List<Question> questionList, int count) {
        Question oldQuestion = questionList.get(count);
        if (newQuestion.getQuestion() != null) oldQuestion.setQuestion(newQuestion.getQuestion());
        if (newQuestion.getOptionA() != null) oldQuestion.setOptionA(newQuestion.getOptionA());
        if (newQuestion.getOptionB() != null) oldQuestion.setOptionB(newQuestion.getOptionB());
        if (newQuestion.getOptionC() != null) oldQuestion.setOptionC(newQuestion.getOptionC());
        if (newQuestion.getOptionD() != null) oldQuestion.setOptionD(newQuestion.getOptionD());
        if (newQuestion.getAnswer() != null) oldQuestion.setAnswer(newQuestion.getAnswer());
        return oldQuestion;
    }

    @Override
    public void deleteQuestion(Long questionNo, QuizPage page) {
        List<Question> questionList = questionRepository.findAllQuestionByQuizPage(page);
        Question question = findQuestionByQuestionNo(questionList, questionNo);
        questionRepository.delete(question);
    }

    @Override
    public void addQuestion(QuizQuestion question, QuizPage page) {
        List<Question> questionList = questionRepository.findAllQuestionByQuizPage(page);
        validateQuestion(question);
        Question question1 = mapper.map(question, Question.class);
        question1.setQuestionNo((questionList.get(questionList.size()-1).getQuestionNo() + 1));
        question1.setQuizPage(page);
        questionRepository.save(question1);
    }

    @Override
    public List<QuizQuestion> findQuestionsFor(QuizPage page) {
        return questionRepository.findAllQuestionByQuizPage(page)
                                 .stream()
                                 .map(QuizQuestion::new)
                                 .toList();
    }

    @Override
    public void deleteQuestions(QuizPage page) {
        List<Question> question = questionRepository.findAllQuestionByQuizPage(page);
        questionRepository.deleteAll(question);
    }

    private Question findQuestionByQuestionNo(List<Question> questionList, Long questionNo) {
        for (Question question : questionList){
            if (question.getQuestionNo().equals(questionNo)) return question;
        }
        throw new QuestionNotFoundException("Question does not exist");
    }

    private boolean answerNotExistInOption(QuizQuestion question) {
        if (question.getOptionA().equals(question.getAnswer())) return false;
        else if (question.getOptionB().equals(question.getAnswer())) return false;
        else if (question.getOptionC().equals(question.getAnswer())) return false;
        else return !question.getOptionD().equals(question.getAnswer());
    }

}
