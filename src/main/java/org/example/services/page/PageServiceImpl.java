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

import java.util.List;

@Service
public class PageServiceImpl implements PageService{
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizPageRepository quizPageRepository;
    @Override
    public void add(String titleQuiz, List<Question> questionList, String description, User user) {
        if (pageExist(titleQuiz)) throw new QuestionTitleExistException("Title already taken");
        QuizPage page = Mapper.mapPage(titleQuiz, user, description);
        quizPageRepository.save(page);
        Long count = 1L;
        for (Question question : questionList){
          questionService.add(question, page, count);
          count += 1;
        }
        quizPageRepository.save(page);
    }

    @Override
    public void updateQuestion(Long questionId, String title, User user, Question newQuestion) {
        if (!pageExist(title)) throw new QuestionTitleExistException("Title does not exist");
        QuizPage page = quizPageRepository.findQuizPageByUser_AndTitle(user, title);
        if (!page.getUser().getId().equals(user.getId())) throw new UserAuthorizeException("User not allowed to perform");
        questionService.update(questionId, page, newQuestion);
    }

    @Override
    public QuizPage findPageByTitleAndUser(String quizTitle, User user) {
        QuizPage page = quizPageRepository.findQuizPageByUser_AndTitle(user, quizTitle);
        if (page != null) return page;
        throw new QuestionTitleExistException("Title does not exist");
    }

    @Override
    public List<Question> readQuestion(String title, User user) {
        QuizPage page = findPageByTitleAndUser(title, user);
        return questionService.findQuestionsFor(page);
    }

    @Override
    public void deleteQuestion(String quizTitle, User user, Long questionNo) {
        QuizPage page = findPageByTitleAndUser(quizTitle, user);
        questionService.deleteQuestion(questionNo, page);
    }

    @Override
    public void addQuestion(Question question, String quizTitle, User user) {
        QuizPage page = findPageByTitleAndUser(quizTitle, user);
        questionService.addQuestion(question, page);
        quizPageRepository.save(page);
    }

    @Override
    public List<QuizPage> viewAllPage() {
        return quizPageRepository.findAll();
    }

    @Override
    public void deletePage(User user, String quizTitle) {
        QuizPage page = findPageByTitleAndUser(quizTitle, user);
        questionService.deleteQuestions(page);
        quizPageRepository.delete(page);
    }

    @Override
    public List<Question> getQuestionsOf(String quizTitle) {
        if (!pageExist(quizTitle)) throw new QuestionTitleExistException("Title does not exist");
        QuizPage page = quizPageRepository.findQuizPageByTitle(quizTitle);
        return questionService.findQuestionsFor(page);

    }

    private boolean pageExist(String titleQuiz) {
        QuizPage quizPage = quizPageRepository.findQuizPageByTitle(titleQuiz);
        return quizPage != null;
    }
}
