package org.example.services.user;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.Role;
import org.example.data.model.User;
import org.example.data.repository.UserRepository;
import org.example.dto.request.*;
import org.example.dto.response.AddQuizResponse;
import org.example.exception.*;
import org.example.services.page.PageService;
import org.example.util.Mapper;
import org.example.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.util.Validation.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PageService pageService;
    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getEmail())) throw new UserExistException("User already exist");
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) throw new PasswordNotMatchExceptions("Passwords does not match");
        if (!validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetailException("Email format was wrong");
        if (!validateDate(registerRequest.getDateOfBirth())) throw new InvalidFormatDetailException("Date format was wrong yyyy/mm/dd");
        if (!validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetailException("Password is weak");
        if (!roleIsNotValid(registerRequest.getRole())) throw new InvalidFormatDetailException("Role must be either teacher or learner");
        User user = Mapper.mapUser(registerRequest);
        userRepository.save(user);
    }

    private boolean userExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    @Override
    public void login(LoginRequest loginRequest) {
        if (!userExist(loginRequest.getEmail())) throw new InvalidLoginDetail("Invalid login detail");
        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        if (!user.isLocked()) throw new ActionDoneException("User already login");
        String encodePassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginRequest.getPassword(), encodePassword)) throw new InvalidLoginDetail("Invalid login detail");
        user.setLocked(false);
        userRepository.save(user);
    }

    @Override
    public AddQuizResponse addQuiz(AddQuizRequest addQuizRequest) {
        if (!userExist(addQuizRequest.getUserEmail())) throw new UserExistException("User does not exist");
        User user = userRepository.findUserByEmail(addQuizRequest.getUserEmail());
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");

        QuizPage page = pageService.add(addQuizRequest.getTitleQuiz(), addQuizRequest.getQuestionList(), addQuizRequest.getDescription(), user);
        user.getQuizPageList().add(page);
        userRepository.save(user);
        return new AddQuizResponse("Quiz added successfully !!!!");
    }
    @Override
    public void deleteQuiz(String userEmail, String quizTitle) {
        if (!userExist(userEmail)) throw new UserExistException("User does not exist");
        User user = userRepository.findUserByEmail(userEmail);
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        QuizPage page = pageService.findPageByTitle(quizTitle, user.getQuizPageList());
        if (!page.getUser().getId().equals(user.getId())) throw new UserAuthorizeException("User not allowed to perform");
        user.getQuizPageList().remove(page);
        userRepository.save(user);
    }

    @Override
    public void updateQuestion(UpdateQuestionRequest request) {
        if (!userExist(request.getEmail())) throw new UserExistException("User does not exist");
        User user = userRepository.findUserByEmail(request.getEmail());
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.updateQuestion(request.getQuestionId(), request.getTitle(), user, request.getNewQuestion());
    }

    @Override
    public List<Question> readQuestion(String title, String email) {
        if (!userExist(email)) throw new UserExistException("User does not exist");
        User user = userRepository.findUserByEmail(email);
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        return pageService.readQuestion(title, user.getQuizPageList());
    }

    @Override
    public void deleteQuestion(String email, String quizTitle, Long questionNo) {
        if (!userExist(email)) throw new UserExistException("User does not exist");
        User user = userRepository.findUserByEmail(email);
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.deleteQuestion(quizTitle, user.getQuizPageList(), questionNo);
    }

    @Override
    public void addQuestion(AddQuestionRequest addQuestionRequest) {
        if (!userExist(addQuestionRequest.getUserEmail())) throw new UserExistException("User does not exist");
        User user = userRepository.findUserByEmail(addQuestionRequest.getUserEmail());
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.addQuestion(addQuestionRequest.getQuestion(), addQuestionRequest.getQuizTitle(), user.getQuizPageList());
    }

    private static boolean roleIsNotValid(String role) {
        for (Role user : Role.values()){
            if (user.toString().equals(role.toUpperCase())) return true;
        }
        return false;
    }

}
