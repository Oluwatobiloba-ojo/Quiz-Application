package org.example.services.user;

import org.example.data.model.Question;
import org.example.data.model.User;
import org.example.dto.request.*;
import org.example.dto.response.AddQuizResponse;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void register(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    AddQuizResponse addQuiz(AddQuizRequest addQuizRequest);
    void deleteQuiz(String userEmail, String quizTitle);
    void updateQuestion(UpdateQuestionRequest request);
    List<Question> readQuestion(String title, String email);
    void deleteQuestion(String email, String gospel, Long questionNo);
    void addQuestion(AddQuestionRequest addQuestionRequest);
}
