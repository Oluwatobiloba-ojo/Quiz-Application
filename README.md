# Champion_QuizApp

## Table Of Content
1. [Introduction](#Introduction)
2. [SetUp](#SetUp)
3. [Teacher-Api](#Teacher-Api)
4. [Learner-Api](#learner-api)

## Introduction
_Champion Quiz Application is a web-based platform designed to facilitate the creation, management. It offers users which are the teachers the ability to create custom quizzes, assign questions with multiple-choice options, and anyone across the platform can take the quizzes. The application aims to provide a user-friendly interface for both quiz creators (Teacher) and participants (Learner), allowing for efficient quiz creation and seamless quiz-taking experiences._

## SetUp
* Create an account with git
* From your terminal/ command prompt clone the repository using this git command
* git clone https://github.com/Oluwatobiloba-ojo/Quiz-Application.git.
* Ensure all dependencies in the project are well injected in your pom.xml.
* To download and build the project, you can run this command on the terminal: mvn clean install
* setup mysql database to configure database connection.
* To start the application from your IDE, run the application main class. Alternatively, you can run this command in the terminal
    * mvn spring-boot:run
* install postman to test the application end-points by providing the necessary url and body requests if necessary.


# Teacher Api:
_The Teacher API provides endpoints for teachers to manage quizzes, including creation, updating, and deletion of quizzes and questions, as well as accessing learner functionalities._
## Features:
* Registration
* Login
* Add quiz 
* Update question 
* add question 
* delete quiz 
* delete question 
* view all quiz's 
* take quiz

## Registration
### Description
*This is the api which is used to register a teacher which takes in the names of the user, email, date of birth, role and the password*

### Password Validation

``Valid Password : Olawale123``

* Starts with an upper case
* Followed by number st least minimum 1

### Email Validation:

``Valid Email: user@gmail.com``
* Username: user
* Domain: gmail

### Date Of Birth Validation:
```
Valid Date of Birth Format: YYYY-MM-DD
Valid Date of Birth: 2001-03-29
```
* The date must start with the year, month and day.
* The separation of concern will be (-)

# Request
* Url: localhost:9090/api/v1/quiz_app/user
* Method: POST
* Header: 
  * Content-type: application/json
* Body
```
JSON
}    
"firstName": "olashile",
"lastName": "Adewuyi",
"email": "delightedadewuyi5@gmail.com",
"dateOfBirth": "2001/03/31",
"password": "Olawale123",
"confirmPassword": "Olawale123",
"role": "Teacher"
}
```
* Fields :
  * `firstname`:*this is the user-first name (required, String)*
  * `lastname`:*this is the user-last name (required, String)*
  * `dateOfBirth`:*this is the date-of-birth of the user (required, String)*
  * `email`:*this is the email of the user (required, String)*
  * `password`:*the is the password of the user (required, String)*
  * `confirmPassword`:*this field is required to confirm the user initial password (required, String)*
  * `role`:*the role the user want ot register for Teacher or Learner*


# Response 1:
*successful request*
* Status code : `201 CREATED`
* Body
```
JSON
{
"message": {
"message": "You have successfully register with the following details !!!!"
},
"successful": true
}
```

# Response 2:
*unsuccesful request due to the user email already used in the application*
* Status Code: `400 BAD REQUEST`
* Body
```
JSON:
{
"message": "User already exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the user date of birth format is wrong*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Date format was wrong yyyy-mm-dd",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the user email format is wrong*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Email format was wrong delightedadewuyise@.com",
"successful": false
}
```

# Response 5:
*unsuccessful request due to that the user role does not exist in the application*
* Status Code : `400 Bad Request`
* Body
```
JSON
{
"message": "Role must be either teacher or learner",
"successful": false
}
```

* Response 6:
*unsuccessful request due to that the user password is weak*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Password is weak",
"successful": false
}
```
# Response 7:
* unsuccessful request due to that if the password does not match the confirmation password.*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Passwords does not match",
"successful": false
}
```

# Login-Request
*This is an end point is to verify the user before accessing basic functionalities of the application. and it takes in the user email and the password*
# Request
* Url : localhost:9090/api/v1/quiz_app/user/login
* Method: `POST`
* Header: Content-type application/json
* Body
```
JSON:
{
"email": "delightedadewuyi5@gmail.com",
"password": "Olawale123"
}
```

# Response 1
*successful request*
* Status Code: `200 OK`
* Body
```
JSON
{
"message": {
"message": "You don login !!!!!"
},
"successful": true
}
```

# Response 2
*unsuccessful request due to that the user has log in into the application already*
* Status Code: `404 Not Found`
* Body
```
JSON:
{
    "message": "User already login",
    "successful": false
}
```

# Response 3:
*unsuccessful request due to that the user does not exist with the email*
* Status Code: 404 Not Found
* Body
```
JSON:
{
"message": "Invalid login detail",
"successful": false
}
```

# Response 4
*unsuccessful request due to that the user password does not match the password used when registering*
* Status Code: 404 Not Found
* Body
```
JSON
{
"message": "Invalid login detail",
"successful": false
}
```

## Add-Quiz Request
*This is the end point which is used by the teacher to add quiz to this platform*
# Request
* Method: `POST`
* Header: Content-Type application/json
* Url: `localhost:9090/api/v1/quiz_app/quiz`
* Body
```
JSON:
{
        "titleQuiz": "Java",
        "description":"A software programming language for programmers",
        "userEmail": "delightedadewuyi5@gmail.com",
        "questionList":[
                            {
                                "question": "what year was java know to the world",
                                "optionA": 1995,
                                "optionB": 1991,
                                "optionC": 1959,
                                "optionD": 2000,
                                "answer": 1995
                            },
                            {
                                "question": "what is the code for compiling java file",
                                "optionA": "java FileName.java",
                                "optionB": "javax FileName",
                                "optionC": "javac FileName",
                                "optionD": "javac FileName.java",
                                "answer": "javac FileName.java"
                            }
        ]
}
```
* Fields:
  * `titleQuiz`: this is the title given to the quiz application (required, String)
  * `description`: this is the description of the quiz application (required, String)
  * `userEmail`: The teacher email used in registration (required, String)
  * `questionList`: The list of question for the application (required, Collection)
  * `question`: this is the question (required, String)
  * `optionA`: the first option (required, String)
  * `optionB`: the second option (required, String)
  * `optionC`: the third option (required, String)
  * `optionD`: The fourth option (required, String)
  * `answer`: the correct answer for the question

# Response 1:
*successful request*
* Status Code: 200 OK
* Body:
```
JSON
{
"message": {
"message": "Quiz added successfully !!!!"
},
"successful": true
}
```

# Response 2:
*unsuccessful request due to that the user does not exist*
* Status Code: 400 Bad Request
* Body:
```
JSON
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the title has already been used already*
* Status Code: 400 Bad Request
* Body
```
JSON:
{
"message": "Title already taken",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that in the collection of questions the answer is not found in the options*
* Status Code:
* Body
```
JSON
{
"message": "Answer does not exist in option",
"successful": false
}
```

## Update_Question
*This end point is used to change update a question in a quiz which is either you want to update the question or options or an answer.*
# Request:
* Url: localhost:9090/api/v1/quiz_app/question/2
* Method: PATCH
* Header: Content-type application/json
* Parameter: 
  * questionNO: the question number (required, Long)
* Body
```
JSON:
{
    "title": "Java",
    "email": "delightedadewuyi5@gmail.com",
    "newQuestion": {
                    "question": "What is the name of the founder of java",
                    "optionA": "Kent beck",
                    "optionB": "Uncle bob",
                    "optionC": "James gosling",
                    "optionD": "Bjarne Stroustrup",
                    "answer": "James gosling"
    }
}
```
* Field:
  * `title`: the title of the quiz which you want to update it question (required, String)
  * `newQuestion`: the updated question (required, Object)
  * `email`: the user email (required, String)
  * `question`: the question (required, String)
  * `optionA`: the option (required, String)
  * `optionB`: the option (required, String)
  * `optionC`: the option (required, String)
  * `optionD`: the option (required, String)
  * `answer`: the answer (required, String)


# Response 1:
*successful request*
* Status Code: 202 Accepted
* Body
```
JSON:
{
"message": {
"message": "Question 4 has been updated successfully"
},
"successful": true
}
```

# Response 2:
*unsuccessful request due to that the user does not exist*
* Status Code: `404 Not Found`
* Body:
```
JSON
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
unsuccessful request due to that the quiz could not be found in the user list of quiz
Status Code: 404 Not Found
Body
```
JSON:
{
"message": "Title does does not exist",
"successful": false
}
```

# Response 4:
_unsuccessful request due to that the question does not exist in that quiz_
* Status code: `404 Not Found`
* Body:
```
JSON:
{
"message": "question does not exist",
"successful": false
}
```

# Response 5:
_unsuccessful request due to that the answer is not found in the options given_
* Status Code:
* Body
```
JSON:
{
"message": "Answer does not exist in options",
"successful": false
}
```

## Delete-Question End-Point
*This end point is used to delete a question from a particular quiz provided you have the quiz title and the questionNo*
# Request:
* Url: localhost:9090/api/v1/quiz_app/question/4?email=delightedAdewuyi5@gmail.com&quizTitle=Java
* Method: DELETE
* Header: 
  * Content-Type application/json
* Parameter:
  * `questionNo`: (required, Number)
  * Query Parameter:
    * `email`: the email of the user (required, String)
    * `quizTitle`: the quizTitle (required, String)

  
# Response 1:
*successful request*
* Status Code: `200 OK`
* Body
```
JSON:
{
"message": {
"message": "4 in Java has been deleted"
},
"successful": true
}
```

# Response 2:
_unsuccessful request due to that the user does not exist_
* Status Code: `400 Bad Request`
* Body:
```
JSON:
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the quiz does not exist*
* Status Code: 400 `Bad Request`
* Body
```
JSON:
{
"message": "Title does does not exist",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the questionNo does not exist*
* Status Code: `400 Bad Request`
* Body:
```
JSON:
{
"message": "Question does not exist",
"successful": false
}
```

# Response 5:
*unsuccessful request due to that the user have not login into the application*
* Status Code:`400 Bad Request`
* Body:
```
JSON:
{
"message": "User have not login",
"successful": false
}
```

# Delete-Quiz End-Point
*This end point is used to delete a quiz out of their collection*
# Request:
* Url: `localhost:9090/api/v1/quiz_app/quiz/delightedAdewuyi5@gmail.com?quizTitle=Java_Docs`
* Method: `DELETE`
* Header: 
  * Content-Type application/json
* Parameter:
  * userEmail: the email of the user(required, String)
  * Query Parameter
    * quizTitle: the quiz you want to delete title (required, String)



# Response 1:
*successful request*
* Status Code: `200 OK`
* Body
```
JSON:
{
"message": {
"message": "Java has been deleted"
},
"successful": true
}
```

# Response 2:
*unsuccessful request due to that the user email does not exist*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the title does not exist*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "Title does not exist",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the user have not login into application*
* Status Code:
* Body
```
JSON:
{
"message": "User have not login",
"successful": false
}
```


## Read-Question End-Point
*This end point is used to allow the teacher to view the questions in this quiz*
# Request:
* Url: localhost:89090/api/v1/quiz_app/question/Java_Docs?email=delightedAdewuyi@gmail.com
* Method: GET
* Parameter:
  * quizTitle: the quiz title to view the question
  * Query Parameter
    * email: the user email (required, String)
* Header:
  * Content-Type: application/json


# Response 1:
*successful request*
* Status Code: 302 Found
* Body
```
JSON:
{
    "message": [
                    {
                        "id": 8,
                        "question": "what year was java know to the world",
                        "optionA": "1995",
                        "optionB": "1991",
                        "optionC": "1959",
                        "optionD": "2000",
                        "answer": "1995",
                        "questionNo": 1,
                        "quizPage": {
                                       "id": 3,
                                       "title": "Java_Docs",
                                        "date": [
                                                 2024,
                                                 2,
                                                 16
                                        ],
                                       "description": "A software programming language for programmers"
                        }
                    },
                    {
                        "id": 9,
                        "question": "what is the code for compiling java file",
                        "optionA": "java FileName.java",
                        "optionB": "javax FileName",
                        "optionC": "javac FileName",
                        "optionD": "javac FileName.java",
                        "answer": "javac FileName.java",
                        "questionNo": 2,
                        "quizPage": {
                                        "id": 3,
                                        "title": "Java_Docs",
                                        "date": [
                                                  2024,
                                                  2,
                                                  16
                                        ],
                                        "description": "A software programming language for programmers"
                        }
                    }
    ],
    "successful": true
}
```

# Response 2:
*unsuccessful request due to that the user does not exist*
* Status Code: 400 Bad Request
* Body
```
JSON:
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the quizTitle does not exist*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "Title does does not exist",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the user have not login into application*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "User have not login",
"successful": false
}
```

# Add-Question End-Point
*This end point is used to add question to a quiz you created yourself*
# Request:
* Url: `localhost:8080/api/v1/quiz_app/question`
* Method: POST
* Header:
  * Content-Type: application/json
* Body
```
JSON:
{
  "quizTitle": "Javas",
  "userEmail": "delightedadewuyi5@gmail.com",
  "question" : {
                 "question": "The four concept of OOP",
                 "optionA": "inheritance, composition, abstraction, polymorphism",
                 "optionB": "association, encapsulation, polymorphism, composition ",
                 "optionC": "inheritance, abstraction, polymorphism, encapsulation",
                 "optionD": "abstraction, polymorphism, composition, encapsulation",
                 "answer": "inheritance, abstraction, polymorphism, encapsulation"
  }
}
```
* Field:
  * `quizTitle`: the title of the quiz which you want to update it question (required, String)
  * `question`: the question (required, Object)
  * `userEmail`: the user email (required, String)
  * `question`: the question (required, String)
  * `optionA`: the option (required, String)
  * `optionB`: the option (required, String)
  * `optionC`: the option (required, String)
  * `optionD`: the option (required, String)
  * `answer`: the answer (required, String)


# Response 1:
*successful request*
* Status Code: `200 OK`
* Body
```
JSON:
{
  "message": {
                "message": "Java_Docs question has been added to title"
  },
  "successful": true
}
```

# Response 2:
*unsuccessful request due to that the user does not exist*
* Status Code: `400 Bad Request`
* Body:
```
JSON:
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the quiz does not exist*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "Title does does not exist",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the user have not login into application*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "User have not login",
"successful": false
}
```

# Response 5:
*unsuccessful request due to that the answer is not find in the question*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "Answer does not exist in option",
"successful": false
}
```



# Learner Api:
_This api ranges from where the learner register, login and the ability for them to take a quiz and view all quiz in the platform_
## Features:
* Registration
* Login
* View-All-Quiz
* Take-A-Quiz


## Registration
### Description
*This is the api which is used to register a teacher which takes in the names of the user, email, date of birth, role and the password*

### Password Validation

``Valid Password : Olawale123``

* Starts with an upper case
* Followed by number st least minimum 1

### Email Validation:

``Valid Email: user@gmail.com``
* Username: user
* Domain: gmail

### Date Of Birth Validation:
```
Valid Date of Birth Format: YYYY-MM-DD
Valid Date of Birth: 2001-03-29
```
* The date must start with the year, month and day.
* The separation of concern will be (-)

# Request
* Url: localhost:9090/api/v1/quiz_app/user
* Method : POST
* Header:
    * Content-type: application/json
* Body
```
JSON
}    
"firstName": "olashile",
"lastName": "Adewuyi",
"email": "delightedadewuyi5@gmail.com",
"dateOfBirth": "2001/03/31",
"password": "Olawale123",
"confirmPassword": "Olawale123",
"role": "Teacher"
}
```
* Fields :
    * `firstname`:*this is the user first name (required, String)*
    * `lastname`:*this is the user last name (required, String)*
    * `dateOfBirth`:*this is the date of birth of the user (required, String)*
    * `email`:*this is the email of the user (required, String)*
    * `password`:*the is the password of the user (required, String)*
    * `confirmPassword`:*this field is required to confirm the user initial password (required, String)*
    * `role`:*the role the user want ot register for Teacher or Learner*


# Response 1:
*successful request*
* Status code : `201 CREATED`
* Body
```
JSON
{
"message": {
"message": "You have successfully register with the following details !!!!"
},
"successful": true
}
```

# Response 2:
*unsuccesful request due to the user email already used in the application*
* Status Code: `400 BAD REQUEST`
* Body
```
JSON:
{
"message": "User already exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the user date of birth format is wrong*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Date format was wrong yyyy-mm-dd",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the user email format is wrong*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Email format was wrong delightedadewuyise@.com",
"successful": false
}
```

# Response 5:
*unsuccessful request due to that the user role does not exist in the application*
* Status Code : `400 Bad Request`
* Body
```
JSON
{
"message": "Role must be either teacher or learner",
"successful": false
}
```

* Response 6:
  *unsuccessful request due to that the user password is weak*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Password is weak",
"successful": false
}
```
# Response 7:
* unsuccessful request due to that if the password does not match the confirm password.*
* Status Code: 400 Bad Request
* Body
```
JSON
{
"message": "Passwords does not match",
"successful": false
}
```

# Login
*This is an end point is to verify the user before accessing basic functionalities of the application. and it takes in the user email and the password*
# Request
* Url : localhost:9090/api/v1/quiz_app/user/login
* Method: `POST`
* Header: Content-type application/json
* Body
```
JSON:
{
"email": "delightedadewuyi5@gmail.com",
"password": "Olawale123"
}
```

# Response 1
*successful request*
* Status Code: `200 OK`
* Body
```
JSON
{
"message": {
"message": "You don login !!!!!"
},
"successful": true
}
```

# Response 2
*unsuccessful request due to that the user have login into the application already*
* Status Code: `404 Not Found`
* Body
```
JSON:
{
    "message": "User already login",
    "successful": false
}
```

# Response 3:
*unsuccessful request due to that the user does not exist with the email*
* Status Code: 404 Not Found
* Body
```
JSON:
{
"message": "Invalid login detail",
"successful": false
}
```

# Response 4
*unsuccessful request due to that the user password does not match the password used when registering*
* Status Code: 404 Not Found
* Body
```
JSON
{
"message": "Invalid login detail",
"successful": false
}
```


## View-All-Quiz End-Point
*This is the end point that return all the quiz in this platform for the user to see which one of them they want to pick*
# Request
* Url: `localhost:9090/api/v1/quiz_app/quiz/holyChild@gmail.com`
* Method: GET
* Header:
  * Content-Type: application/json
* Parameter:
  * userEmail: the email of the user (required, String)


# Response 1:
*successful request*
* Status Code: `200 OK`
* Body
```
JSON:
{
    "message": [ 
                  {
                      "id": 3,
                      "title": "Java_Docs",
                      "date": [
                                  2024,
                                  2,
                                  16
                      ],
                     "description": "A software programming language for programmers"
                  },
                  {
                      "id": 4,
                      "title": "Java_Documentation",
                      "date": [
                                  2024,
                                  3,
                                  29
                      ],
                      "description": "A software programming language for programmers"
                  },
                  {
                       "id": 5,
                      "title": "Java_Documentation 2",
                       "date": [
                                    2024,
                                    3,
                                    29
                       ],
                       "description": "A software programming language for programmers"
                  }
    ],
    "successful": true
}
```

# Response 2:
*unsuccessful request due to user does not exist*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the user have not login to application*
* Status Code: `400 Bad Request`
* Body
```
JSON:
{
"message": "User have not login",
"successful": false
}
```

# Take-Quiz End-Point
*This end-point is used to let the user see the question with the options but not with the answer*
# Request:
* Url: `localhost:9090/api/v1/quiz_app/quiz/Java?email=holyChild@gmail.com`
* Method: POST
* Header:
  * Content-Type: application/json
* Parameter:
  * quizTitle: the title of the quiz you want to take (required, String)
  * Query Parameter:
    * email: the email of the user(required, String)



# Response 1:
*successful request*
* Status Code: `202 Accepted`
* Body
```
JSON:
{
    "message": [
                    {
                        "title": "Java_Docs",
                        "question": "what year was java know to the world",
                        "answer": "1995",
                        "optionA": "1995",
                        "optionB": "1991",
                        "optionC": "1959",
                        "optionD": "2000"
                    },
                    {
                        "title": "Java_Docs",
                        "question": "what is the code for compiling java file",
                        "answer": "javac FileName.java",
                        "optionA": "java FileName.java",
                        "optionB": "javax FileName",
                        "optionC": "javac FileName",
                        "optionD": "javac FileName.java"
                    },
                    {
                        "title": "Java_Docs",
                        "question": "The four concept of OOP",
                        "answer": "inheritance, abstraction, polymorphism, encapsulation",
                        "optionA": "inheritance, composition, abstraction, polymorphism",
                        "optionB": "association, encapsulation, polymorphism, composition ",
                        "optionC": "inheritance, abstraction, polymorphism, encapsulation",
                        "optionD": "abstraction, polymorphism, composition, encapsulation"
                    }
    ],
    "successful": true
}
```


# Response 2:
*unsuccessful request due to user does not exist*
* Status Code: 404 Not Found
* Body
```
JSON
{
"message": "User does not exist",
"successful": false
}
```

# Response 3:
*unsuccessful request due to that the user have not login to application*
* Status Code: `404 Not Found`
* Body
```
JSON:
{
"message": "User have not login",
"successful": false
}
```

# Response 4:
*unsuccessful request due to that the quiz does not exist*
* Status Code: `404 Not Found`
* Body
```
JSON:
{
"message": "Title does not exist",
"successful": false
}
```

