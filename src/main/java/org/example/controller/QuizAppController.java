package org.example.controller;

import org.example.dto.request.*;
import org.example.dto.response.*;
import org.example.exception.QuizAppException;
import org.example.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz_app")
public class QuizAppController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public void display() {
        System.out.println("You are a boy");
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.register(registerRequest)), HttpStatus.CREATED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.login(loginRequest)), HttpStatus.OK);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> addQuiz(@RequestBody AddQuizRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.addQuiz(request)), HttpStatus.CREATED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/question/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable("questionId") Long questionId, @RequestBody UpdateQuestionRequest request) {
        try {
            request.setQuestionId(questionId);
            return new ResponseEntity<>(new ApiResponse(true, userService.updateQuestion(request)), HttpStatus.ACCEPTED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/quiz/{userEmail}")
    public ResponseEntity<?> viewAllQuiz(@PathVariable("userEmail") String userEmail) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.viewAllQuiz(userEmail)), HttpStatus.OK);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/question/{quizTitle}")
    public ResponseEntity<?> readQuestion(@PathVariable("quizTitle") String quizTitle,
                                          @RequestParam(name = "email") String email) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.readQuestion(quizTitle, email)), HttpStatus.FOUND);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestBody AddQuestionRequest addQuestionRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.addQuestion(addQuestionRequest)), HttpStatus.CREATED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/quiz/{userEmail}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("userEmail") String userEmail,
                                        @RequestParam(name = "quizTitle") String quizTitle) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.deleteQuiz(userEmail, quizTitle)), HttpStatus.ACCEPTED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/question/{questionNo}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionNo") Long questionNo,
                                            @RequestParam(name = "quizTitle") String quizTitle,
                                            @RequestParam(name = "email") String email) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.deleteQuestion(email, quizTitle, questionNo)), HttpStatus.OK);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/quiz/{quizTitle}")
    public ResponseEntity<?> takeQuiz(@PathVariable("quizTitle") String quizTitle,
                                      @RequestParam(name = "email") String email) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.takeQuiz(quizTitle, email)), HttpStatus.ACCEPTED);
        } catch (QuizAppException exception) {
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/teacher/quiz/{email}")
    public ResponseEntity<?> viewQuizBelonging(@PathVariable("email") String email){
        try{
            return new ResponseEntity<>(new ApiResponse(true, userService.viewQuizCreatedBy(email)), HttpStatus.FOUND);
        }catch (QuizAppException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
