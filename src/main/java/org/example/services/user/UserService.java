package org.example.services.user;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.dto.request.*;
import org.example.dto.response.*;

import java.util.List;

public interface UserService {

    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    AddQuizResponse addQuiz(AddQuizRequest addQuizRequest);
    DeleteQuizResponse deleteQuiz(String userEmail, String quizTitle);
    UpdateQuestionResponse updateQuestion(UpdateQuestionRequest request);
    List<Question> readQuestion(String title, String email);
    DeleteQuestionResponse deleteQuestion(String email, String quizTitle, Long questionNo);
    AddQuestionResponse addQuestion(AddQuestionRequest addQuestionRequest);
    List<QuizPage> viewAllQuiz(String email);
    List<Question> takeQuiz(String quizTitle, String email);
}
