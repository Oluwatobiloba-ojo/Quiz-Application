package org.example.util;

import org.example.data.model.Question;
import org.example.data.model.QuizPage;
import org.example.data.model.Role;
import org.example.data.model.User;
import org.example.dto.request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Mapper {


    public static User mapUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setDateOfBirth(registerRequest.getDateOfBirth());
        return user;
    }

    private static String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static QuizPage mapPage(String titleQuiz, User user, String description) {
        QuizPage page = new QuizPage();
        page.setUser(user);
        page.setTitle(titleQuiz);
        page.setDescription(description);
        return page;
    }
}
