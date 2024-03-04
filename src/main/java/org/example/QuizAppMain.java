package org.example;

import org.example.data.model.Question;
import org.example.services.question.QuestionService;
import org.example.services.question.QuestionServiceImpl;
import org.example.services.user.UserService;
import org.example.services.user.UserServiceImpl;

import javax.swing.*;
import java.util.List;

public class QuizAppMain {
    private static final UserService userService = new UserServiceImpl();
    private static final QuestionService questionService = new QuestionServiceImpl();

    public static void takeQuiz(String titleQuiz, String userEmail){
        List<Question> questionList = userService.takeQuiz(titleQuiz, userEmail);
        int count = 0;
        for (Question question : questionList) {
            String[] options = new String[]{question.getOptionA(), question.getOptionB(),
                    question.getOptionC(), question.getOptionD()};
            var selection = JOptionPane.showOptionDialog(null, question.getQuestion(), "Question time",
                    JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (question.getAnswer().equals(options[selection-1]))count++;
        }
        if (count < questionList.size()){
            JOptionPane.showMessageDialog(null, "You scored "+count);
        }else {
            JOptionPane.showMessageDialog(null, "Scores everything");
        }

    }

    public static void main(String[] args) {
        String userEmail = JOptionPane.showInputDialog("Enter your userEmail");
        String titleQuiz = JOptionPane.showInputDialog("Enter your desired title");

        takeQuiz(userEmail, titleQuiz);
    }

}
