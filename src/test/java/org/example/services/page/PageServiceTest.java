package org.example.services.page;

import jakarta.transaction.Transactional;
import org.example.data.model.User;
import org.example.dto.response.QuizPageResponse;
import org.example.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@TestPropertySource("/test.properties")
@Transactional
class PageServiceTest {

    @Autowired
    private PageService pageService;
    @Autowired
    private UserService userService;


    @Test
    @Sql(scripts = "/scripts/insert.sql")
    public void testThatTeacherGetTheQuizCreatedByThem(){
        User user = userService.findUserBy("test@gmail.com");
        List<QuizPageResponse> response = pageService.findPagesBelongingTo(user);
        System.out.println(response);
        assertThat(response.size()).isEqualTo(2);
        assertThat(pageService.viewAllPage().size()).isEqualTo(3);
    }


}