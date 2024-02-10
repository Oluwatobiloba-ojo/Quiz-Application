package org.example.services.user;

import org.example.data.model.Question;
import org.example.data.repository.QuestionRepository;
import org.example.data.repository.QuizPageRepository;
import org.example.data.repository.UserRepository;
import org.example.dto.request.*;
import org.example.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    QuizPageRepository quizPageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    RegisterRequest registerRequest;
    LoginRequest loginRequest;
    @AfterEach
    public void startAfterEachTest(){
        userRepository.deleteAll();
        questionRepository.deleteAll();
        quizPageRepository.deleteAll();
    }
    @BeforeEach
    public void setUpUserService(){
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("deborahdelighted5@gmail.com");
        registerRequest.setDateOfBirth("2001/04/24");
        registerRequest.setFirstName("delight");
        registerRequest.setLastName("adewuyi");
        registerRequest.setPassword("Olawale123");
        registerRequest.setConfirmPassword("Olawale123");
        registerRequest.setRole("Teacher");
        loginRequest = new LoginRequest();
        loginRequest.setEmail(registerRequest.getEmail());
        loginRequest.setPassword(registerRequest.getPassword());
    }

    @Test
    public void testThatUserWhenWantToRegisterWithInvalidEmailThrowsException(){
        registerRequest.setEmail("wrongEmailFormat");
        assertThrows(InvalidFormatDetailException.class, () -> userService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserWantToRegisterWithAnInvalidDateOfBirthFormatThrowsException(){
        registerRequest.setDateOfBirth("24/2001/04");
        assertThrows(InvalidFormatDetailException.class, ()-> userService.register(registerRequest));
    }
    @Test
    public void testThatWhenUserWantToRegisterWithAWeakPasswordThrowsException(){
        registerRequest.setPassword("wrongFormat");
        registerRequest.setConfirmPassword(registerRequest.getPassword());
        assertThrows(InvalidFormatDetailException.class, ()-> userService.register(registerRequest));
    }
    @Test
    public void tesThatIfConfirmPasswordAndPasswordAreNotTheSameThrowsException(){
        registerRequest.setConfirmPassword("Tobi1234");
        registerRequest.setPassword("Ola1234");
        assertThrows(PasswordNotMatchExceptions.class, ()-> userService.register(registerRequest));
    }
    @Test
    public void testThatWhenRoleThatAUserGiveIsWrongThrowsException(){
        registerRequest.setRole("wrongRole");
        assertThrows(InvalidFormatDetailException.class, () -> userService.register(registerRequest));
    }
    @Test
    public void testThatUserCanNotRegisterAgainWithSameEmailThrowsException(){
        userService.register(registerRequest);
        assertThrows(UserExistException.class, ()-> userService.register(registerRequest));
    }
    @Test
    public void testThatUserWhenHasRegisterTryToLoginIntoAccountThrowExceptionIfPasswordIsWrong(){
        userService.register(registerRequest);
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidLoginDetail.class, ()-> userService.login(loginRequest));
    }
    @Test
    public void testThatUserCanNotLoginIfTheUserDoesNotExistThroeException(){
        userService.register(registerRequest);
        loginRequest.setEmail("invalidEmail");
        assertThrows(InvalidLoginDetail.class, ()-> userService.login(loginRequest));
    }
    @Test
    public void testThatUserCanNotLoginWhileHeHasLoginAlready(){
        userService.register(registerRequest);
        userService.login(loginRequest);
        assertThrows(ActionDoneException.class, () -> userService.login(loginRequest));
    }
    @Test
    public void testThatWhenUserWantToAddQuizAndUserDoesNotExistThrowsException(){
        List<Question> questionList = new ArrayList<>();
        Question question1 = setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
        questionList.add(question1);
        question1 = setQuestion("Where was jesus born","Manager","Jerusalem","Manager","Europe","Egypt");
        questionList.add(question1);
        AddQuizRequest addQuizRequest = new AddQuizRequest();
        addQuizRequest.setTitleQuiz("Gospel");
        addQuizRequest.setUserEmail("invalid_user");
        addQuizRequest.setDescription("A gospel quiz for student and all");
        addQuizRequest.setQuestionList(questionList);
        assertThrows(UserExistException.class, () -> userService.addQuiz(addQuizRequest));
    }

    @Test
    public void testThatWhenUserHasRegisterButHaveNotLoginAndWantToAddQuestionThrowsException(){
        userService.register(registerRequest);
        List<Question> questionList = new ArrayList<>();

        Question question1 =  setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
        questionList.add(question1);


        AddQuizRequest addQuizRequest = new AddQuizRequest();
        addQuizRequest.setTitleQuiz("Gospel");
        addQuizRequest.setUserEmail(registerRequest.getEmail());
        addQuizRequest.setDescription("A gospel quiz for student and all");
        addQuizRequest.setQuestionList(questionList);
        assertThrows(InvalidLoginDetail.class, () -> userService.addQuiz(addQuizRequest));
    }
    @Test
    public void testThatIfOurQuestionIsEmptyThrowsException(){
        userService.register(registerRequest);
        userService.login(loginRequest);
        List<Question> questionList = new ArrayList<>();
        Question question = new Question();
        questionList.add(question);

        AddQuizRequest request = new AddQuizRequest();
        request.setTitleQuiz("Gospel");
        request.setDescription("christians quiz");
        request.setQuestionList(questionList);
        request.setUserEmail(registerRequest.getEmail());
        assertThrows(QuestionBlankException.class, ()-> userService.addQuiz(request));
    }

    @Test
    public void testThatWhenUserHaveLoginAndRegisterAddQuizWithTitleWhichAlreadyExistThrowsException(){
        userService.register(registerRequest);
        userService.login(loginRequest);

        List<Question> questionList = new ArrayList<>();
        Question question1 =  setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
        questionList.add(question1);
        Question question = setQuestion("Where was jesus born","Manager","Jerusalem","Manager","Europe","Egypt");
        questionList.add(question);

        AddQuizRequest addQuizRequest = new AddQuizRequest();
        addQuizRequest.setTitleQuiz("Gospel");
        addQuizRequest.setUserEmail(registerRequest.getEmail());
        addQuizRequest.setDescription("A gospel quiz for student and all");
        addQuizRequest.setQuestionList(questionList);
        userService.addQuiz(addQuizRequest);
        assertThrows(QuestionTitleExistException.class, ()-> userService.addQuiz(addQuizRequest));
    }
   @Test
   public void testThatWhenUserAddsQuizAndAnswerIsNotFoundWithinThoseOptionThrowsException(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 =  setQuestion("when was jesus brought to the temple", "age", "12", "17", "15", "21");
       questionList.add(question1);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       assertThrows(AnswerNotFoundException.class, () -> userService.addQuiz(addQuizRequest));
   }
   @Test
    public void testThatUserCanDeleteQuizPageAndWantToDeleteItWithInvalidUserThrowsException(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 = setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
       questionList.add(question1);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);
       assertThrows(UserExistException.class, () -> userService.deleteQuiz("invalidUser", "Gospel"));
   }
   @Test
    public void testThatWhenUserWantToDeleteAQuizAndHasNoRightToDeleteItAsHeIsNotTheOwnerThrowsException(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 = setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
       questionList.add(question1);
       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);

       registerRequest.setEmail("ojot630@gmail.com");
       userService.register(registerRequest);
       loginRequest.setEmail(registerRequest.getEmail());
       userService.login(loginRequest);
       assertThrows(QuestionTitleExistException.class, ()-> userService.deleteQuiz(registerRequest.getEmail(), addQuizRequest.getTitleQuiz()));
   }
   @Test
    public void testThatPageWhenDeletedTwiceThrowsException(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 = setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
       questionList.add(question1);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);

       userService.deleteQuiz(registerRequest.getEmail(), addQuizRequest.getTitleQuiz());
       assertThrows(QuestionTitleExistException.class, ()-> userService.deleteQuiz(registerRequest.getEmail(), addQuizRequest.getTitleQuiz()));
   }
   @Test
   public void testThatWhenQuizHaveBeenAddedAndWantToBeUpdatedWithWrongQuestionIdThrowsException(){
        userService.register(registerRequest);
        userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 = setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
       questionList.add(question1);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);

       Question newQuestion = setQuestion("What is the name of jesus earthly father", "Joseph", "mark", "John", "Joseph", "Joshua");
       UpdateQuestionRequest request = new UpdateQuestionRequest();
       request.setTitle("Gospel");
       request.setNewQuestion(newQuestion);
       request.setQuestionId(20L);
       request.setEmail(registerRequest.getEmail());
       assertThrows(QuestionNotFoundException.class, ()-> userService.updateQuestion(request));
   }
   @Test
    public void testThatWhenUserHaveAddedQuestionsAQuestionCanBeDeletedFromListOfQuestion(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 = setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");
       questionList.add(question1);
       Question question = setQuestion("Where was jesus born","Manager","Jerusalem","Manager","Europe","Egypt");
       questionList.add(question);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);

       assertEquals(2, userService.readQuestion("Gospel", registerRequest.getEmail()).size());
       userService.deleteQuestion(registerRequest.getEmail(), "Gospel", 1L);
       assertEquals(1, userService.readQuestion("Gospel", registerRequest.getEmail()).size());
   }
   @Test
   public void testThatQuestionsWhenItHasBeenAddedWeCanAddQuestionToItLaterButIfTitleIsWrongThrowException(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 =  setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");;
       questionList.add(question1);
       Question question = setQuestion("Where was jesus born","Manager","Jerusalem","Manager","Europe","Egypt");
       questionList.add(question);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);

       Question newQuestion = setQuestion("What is the two sister of lazarus", "Mary and Martha",
               "Mary and Racheal","Racheal and Martha","Mary and Martha","Martha and Sarah" );
       AddQuestionRequest addQuestionRequest = new AddQuestionRequest();
       addQuestionRequest.setQuizTitle("gospel");
       addQuestionRequest.setUserEmail(registerRequest.getEmail());
       addQuestionRequest.setQuestion(newQuestion);
       assertThrows(QuestionTitleExistException.class, () -> userService.addQuestion(addQuestionRequest));
   }
   @Test
   public void testThatWhenQuizHaveBeenAddedUserCanStillAddQuestionToTheQuiz(){
       userService.register(registerRequest);
       userService.login(loginRequest);

       List<Question> questionList = new ArrayList<>();
       Question question1 =  setQuestion("when was jesus brought to the temple", "12", "12", "17", "15", "21");;
       questionList.add(question1);
       Question question = setQuestion("Where was jesus born","Manager","Jerusalem","Manager","Europe","Egypt");
       questionList.add(question);

       AddQuizRequest addQuizRequest = new AddQuizRequest();
       addQuizRequest.setTitleQuiz("Gospel");
       addQuizRequest.setUserEmail(registerRequest.getEmail());
       addQuizRequest.setDescription("A gospel quiz for student and all");
       addQuizRequest.setQuestionList(questionList);
       userService.addQuiz(addQuizRequest);

       Question newQuestion = setQuestion("What is the two sister of lazarus", "Mary and Martha",
               "Mary and Racheal","Racheal and Martha","Mary and Martha","Martha and Sarah" );

       AddQuestionRequest addQuestionRequest = new AddQuestionRequest();
       addQuestionRequest.setQuizTitle("Gospel");
       addQuestionRequest.setUserEmail(registerRequest.getEmail());
       addQuestionRequest.setQuestion(newQuestion);
       assertEquals(2, userService.readQuestion("Gospel", registerRequest.getEmail()).size());
       userService.addQuestion(addQuestionRequest);
       assertEquals(3, userService.readQuestion("Gospel", registerRequest.getEmail()).size());
   }



    private static Question setQuestion(String question, String answer, String optionA,String optionB,String optionC,String optionD ) {
        Question question1 = new Question();
        question1.setQuestion(question);
        question1.setOptionA(optionA);
        question1.setOptionB(optionB);
        question1.setOptionD(optionD);
        question1.setOptionC(optionC);
        question1.setAnswer(answer);
        return question1;
    }






}