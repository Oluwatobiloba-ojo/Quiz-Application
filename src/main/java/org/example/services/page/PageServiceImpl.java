package org.example.services.page;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.User;
import org.example.data.repository.QuizPageRepository;
import org.example.exception.QuestionTitleExistException;
import org.example.exception.UserAuthorizeException;
import org.example.services.question.QuestionService;
import org.example.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageServiceImpl implements PageService{
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizPageRepository quizPageRepository;
    @Override
    public QuizPage add(String titleQuiz, List<Question> questionList, String description, User user) {
        if (pageExist(titleQuiz)) throw new QuestionTitleExistException("Title already taken");
        QuizPage page = Mapper.mapPage(titleQuiz, user, description);
        quizPageRepository.save(page);
        List<Question> questions = new ArrayList<>();
        Long count = 1L;
        for (Question question : questionList){
          Question question1 = questionService.add(question, page, count);
          questions.add(question1);
          count += 1;
        }
        page.setQuestionList(questions);
        return quizPageRepository.save(page);
    }

    @Override
    public void updateQuestion(Long questionId, String title, User user, Question newQuestion) {
        if (!pageExist(title)) throw new QuestionTitleExistException("Title does not exist");
        QuizPage page = quizPageRepository.findQuizPageByTitle(title);
        if (!page.getUser().getId().equals(user.getId())) throw new UserAuthorizeException("User not allowed to perform");
        questionService.update(questionId, page.getQuestionList(), newQuestion);
    }

    @Override
    public QuizPage findPageByTitle(String quizTitle, List<QuizPage> quizPageList) {
        for (QuizPage page : quizPageList){
            if (page.getTitle().equals(quizTitle)) return page;
        }
        throw new QuestionTitleExistException("Title does not exist");
    }

    @Override
    public List<Question> readQuestion(String title, List<QuizPage> quizPageList) {
        QuizPage page = findPageByTitle(title, quizPageList);
        return page.getQuestionList();
    }

    @Override
    public void deleteQuestion(String quizTitle, List<QuizPage> quizPageList, Long questionNo) {
        QuizPage page = findPageByTitle(quizTitle, quizPageList);
        questionService.deleteQuestion(questionNo, page.getQuestionList());
    }

    @Override
    public void addQuestion(Question question, String quizTitle, List<QuizPage> quizPageList) {
        QuizPage page = findPageByTitle(quizTitle, quizPageList);
        Question question1 = questionService.addQuestion(page.getQuestionList(), question, page);
        page.getQuestionList().add(question1);
        quizPageRepository.save(page);
    }

    private boolean pageExist(String titleQuiz) {
        QuizPage quizPage = quizPageRepository.findQuizPageByTitle(titleQuiz);
        return quizPage != null;
    }
}
