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

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse response = new RegisterResponse();
        try {
            userService.register(registerRequest);
            response.setMessage("You have successfully register with the following details !!!!");
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse response = new LoginResponse();
        try {
            userService.login(loginRequest);
            response.setMessage("You don login !!!!!");
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/quiz")
    public ResponseEntity<?> addQuiz(@RequestBody AddQuizRequest request){
        AddQuizResponse response = new AddQuizResponse();
        try {
            response = userService.addQuiz(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/question/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable("questionId") Long questionId,@RequestBody UpdateQuestionRequest request){
        UpdateQuestionResponse response = new UpdateQuestionResponse();
        try {
            request.setQuestionId(questionId);
            userService.updateQuestion(request);
            response.setMessage(String.format("Question %s has been updated successfully",questionId));
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.ACCEPTED);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/quiz/{userEmail}")
    public ResponseEntity<?> viewAllQuiz(@PathVariable("userEmail") String userEmail){
        ViewAllQuizResponse viewAllQuizResponse = new ViewAllQuizResponse();
        try{
            viewAllQuizResponse.setMessage(userService.viewAllQuiz(userEmail));
            return new ResponseEntity<>(new ApiResponse(true, viewAllQuizResponse), HttpStatus.OK);
        }catch (QuizAppException exception){
            viewAllQuizResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, viewAllQuizResponse), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/question/{quizTitle}")
    public ResponseEntity<?> readQuestion(@PathVariable("quizTitle") String quizTitle,
                                          @RequestParam(name = "email") String email){
        ReadQuestionResponse response = new ReadQuestionResponse();
        try {
            response.setMessage(userService.readQuestion(quizTitle, email));
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.FOUND);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestBody AddQuestionRequest addQuestionRequest){
        AddQuestionResponse response = new AddQuestionResponse();
        try {
            userService.addQuestion(addQuestionRequest);
            response.setMessage(String.format("%s question has been added to title", addQuestionRequest.getQuizTitle()));
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/quiz/{userEmail}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("userEmail") String userEmail,
                                        @RequestParam(name = "quizTitle") String quizTitle){
        DeleteQuizResponse response = new DeleteQuizResponse();
        try {
            userService.deleteQuiz(userEmail, quizTitle);
            response.setMessage(String.format("%s has been deleted", quizTitle));
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.ACCEPTED);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/question/{questionNo}")
    public ResponseEntity<?> deleteQuestion (@PathVariable("questionNo") Long questionNo,
                                             @RequestParam(name = "quizTitle") String quizTitle,
                                            @RequestParam(name = "email") String email){
        DeleteQuestionResponse response = new DeleteQuestionResponse();
        try{
            userService.deleteQuestion(email, quizTitle, questionNo);
            response.setMessage(String.format("%s in %s has been deleted", questionNo, quizTitle));
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        }catch (QuizAppException exception){
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, response), HttpStatus.BAD_REQUEST);
        }
    }



}
