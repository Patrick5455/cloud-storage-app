package com.udacity.jwdnd.course1.cloudstorage.services.crudservices;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(MockitoJUnitRunner.class)
@DisplayName("user services test")

class UserServiceTest {


    @Mock
    UserMapper userMapperMock;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    static   User user = new User();

    @BeforeAll
    public static void beforeAll(){
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testUser01");
        user.setSalt("salt123#");
        user.setPassword("hashedpassword123#");
       // userMapper.insertUser(user);
    }

    @Test
    public void userCanSignUp() throws Exception{
        int userid = userMapperMock.insertUser(user);
        logger.info("userid {}", userid);
        assertTrue(userid > 0, "user not inserted into db");
    }
}
