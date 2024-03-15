package org.example.controller;

import org.example.dto.response.ApiResponse;
import org.example.exception.QuizAppException;
import org.example.services.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    @Autowired
    PageService pageService;

    @GetMapping("/question/{quizTitle}")
    public ResponseEntity<?> takeQuiz(@PathVariable("quizTitle") String quizTitle) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, pageService.getQuestionsOf(quizTitle)), HttpStatus.ACCEPTED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
