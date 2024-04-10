package org.example.services.user;

import org.example.data.model.*;
import org.example.data.repository.UserRepository;
import org.example.dto.request.*;
import org.example.dto.response.*;
import org.example.exception.*;
import org.example.services.page.PageService;
import org.example.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.util.Validation.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    PageService pageService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (!userExist(registerRequest.getEmail())) throw new UserExistException("User already exist");
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) throw new PasswordNotMatchExceptions("Passwords does not match");
        if (!validateEmail(registerRequest.getEmail())) throw new InvalidFormatDetailException("Email format was wrong "+ registerRequest.getEmail());
        if (!validateDate(registerRequest.getDateOfBirth())) throw new InvalidFormatDetailException("Date format was wrong yyyy-mm-dd");
        if (!validatePassword(registerRequest.getPassword())) throw new InvalidFormatDetailException("Password is weak");
        if (!roleIsNotValid(registerRequest.getRole())) throw new InvalidFormatDetailException("Role must be either teacher or learner");
        User user = Mapper.mapUser(registerRequest);
        userRepository.save(user);
        RegisterResponse response = new RegisterResponse();
        response.setMessage("You have successfully register with the following details !!!!");
        return response;
    }

    private boolean userExist(String email) {
        return userRepository.findUserByEmail(email).isEmpty();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (userExist(loginRequest.getEmail())) throw new InvalidLoginDetail("Invalid login detail");
        User user = findUserBy(loginRequest.getEmail());
        if (!user.isLocked()) throw new ActionDoneException("User already login");
        String encodePassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginRequest.getPassword(), encodePassword)) throw new InvalidLoginDetail("Invalid login detail");
        user.setLocked(false);
        userRepository.save(user);
        LoginResponse response = new LoginResponse();
        response.setRole(user.getUserRole());
        response.setMessage("You don login !!!!!");
        return response;
    }

    @Override
    public AddQuizResponse addQuiz(AddQuizRequest addQuizRequest) {
        User user = findUserBy(addQuizRequest.getUserEmail());
        if(user.getUserRole() == Role.LEARNER) throw new UserAuthorizeException("Learner not allowed to access this method");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.add(addQuizRequest.getTitleQuiz(), addQuizRequest.getQuestionList(), addQuizRequest.getDescription(), user);
        userRepository.save(user);
        return new AddQuizResponse("Quiz added successfully !!!!");
    }
    @Override
    public DeleteQuizResponse deleteQuiz(String userEmail, String quizTitle) {
        if (userExist(userEmail)) throw new UserExistException("User does not exist");
        User user = findUserBy(userEmail);
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.deletePage(user, quizTitle);
        DeleteQuizResponse response = new DeleteQuizResponse();
        response.setMessage(String.format("%s has been deleted", quizTitle));
        return response;
    }

    @Override
    public UpdateQuestionResponse updateQuestion(UpdateQuestionRequest request) {
        if (userExist(request.getEmail())) throw new UserExistException("User does not exist");
        User user = findUserBy(request.getEmail());
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.updateQuestion(request.getQuestionId(), request.getTitle(), user, request.getNewQuestion());
        UpdateQuestionResponse response = new UpdateQuestionResponse();
        response.setMessage(String.format("Question %s has been updated successfully",request.getQuestionId()));
        return response;
    }

    @Override
    public List<QuizQuestion> readQuestion(String title, String email) {
        if (userExist(email)) throw new UserExistException("User does not exist");
        User user = findUserBy(email);
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        return pageService.readQuestion(title, user);
    }

    @Override
    public DeleteQuestionResponse deleteQuestion(String email, String quizTitle, Long questionNo) {
        if (userExist(email)) throw new UserExistException("User does not exist");
        User user = findUserBy(email);
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.deleteQuestion(quizTitle, user, questionNo);
        DeleteQuestionResponse response = new DeleteQuestionResponse();
        response.setMessage(String.format("%s in %s has been deleted", questionNo, quizTitle));
        return response;
    }

    @Override
    public AddQuestionResponse addQuestion(AddQuestionRequest addQuestionRequest) {
        if (userExist(addQuestionRequest.getUserEmail())) throw new UserExistException("User does not exist");
        User user = findUserBy(addQuestionRequest.getUserEmail());
        if (!user.getUserRole().equals(Role.TEACHER)) throw new UserAuthorizeException("User not allowed to perform");
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        pageService.addQuestion(addQuestionRequest.getQuestion(), addQuestionRequest.getQuizTitle(), user);
        AddQuestionResponse response = new AddQuestionResponse();
        response.setMessage(String.format("%s question has been added to title", addQuestionRequest.getQuizTitle()));
        return response;
    }

    @Override
    public List<QuizPageResponse> viewAllQuiz(String email) {
        if (userExist(email)) throw new UserExistException("User does not exist");
        if (findUserBy(email).isLocked()) throw new InvalidLoginDetail("User have not login");
        return pageService.viewAllPage();
    }

    @Override
    public List<QuizQuestion> takeQuiz(String quizTitle, String email) {
        if (userExist(email)) throw new UserExistException("user does not exist");
        User user = findUserBy(email);
        if (user.isLocked()) throw new InvalidLoginDetail("User have not login");
        return pageService.getQuestionsOf(quizTitle);
    }

    @Override
    public User findUserBy(String userMail) {
        return userRepository.findUserByEmail(userMail).orElseThrow(() -> new UserExistException("User does not exist"));
    }

    @Override
    public List<QuizPageResponse> viewQuizCreatedBy(String userMail) {
        User user = findUserBy(userMail);
        if (user.getUserRole().equals(Role.LEARNER)) throw new UserAuthorizeException("User not authorize");
        return pageService.findPagesBelongingTo(user);
    }

    private static boolean roleIsNotValid(String role) {
        for (Role user : Role.values()){
            if (user.toString().equals(role.toUpperCase())) return true;
        }
        return false;
    }

}
