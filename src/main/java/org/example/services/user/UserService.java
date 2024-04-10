package org.example.services.user;

import org.example.data.model.QuizQuestion;
import org.example.data.model.User;
import org.example.dto.request.*;
import org.example.dto.response.*;

import java.util.List;

public interface UserService {

    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    AddQuizResponse addQuiz(AddQuizRequest addQuizRequest);
    DeleteQuizResponse deleteQuiz(String userEmail, String quizTitle);
    UpdateQuestionResponse updateQuestion(UpdateQuestionRequest request);
    List<QuizQuestion> readQuestion(String title, String email);
    DeleteQuestionResponse deleteQuestion(String email, String quizTitle, Long questionNo);
    AddQuestionResponse addQuestion(AddQuestionRequest addQuestionRequest);
    List<QuizPageResponse> viewAllQuiz(String email);
    List<QuizQuestion> takeQuiz(String quizTitle, String email);
    User findUserBy(String userMail);
    List<QuizPageResponse> viewQuizCreatedBy(String userMail);
}
