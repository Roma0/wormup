package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.User;
import com.ascending.repository.UserDao;
import io.jsonwebtoken.Claims;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class JwtServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired JwtService jwtService;
    @Autowired UserService userService;
    @Autowired UserDao userDao;

    private User user;
    private Long id;
    private final String name = "xf";
    private final String firstName = "Xiong";
    private final String lastName = "Fei";
    private final String email = "xf@gmail.com";
    private final String password = "xf123!";
//    private String token;

    @Before
    public void setUp(){
        user = new User(name, password, firstName,lastName, email);

        id = userService.save(user).getId();
        assert ( id != null);
        logger.debug("User is setup before testing,");
    }

    @After
    public void tearDown(){
        userDao.delete(user);
        assert (userDao.findById(id) == null);
        logger.debug("User is teared down from the database after testing.");
    }

    @Test
    public void generateToken(){
        String token;
        token = jwtService.generateToken(user);
        Assert.assertEquals(3, token.split("\\.").length);
    }

    @Test
    public void decryptJwtToken(){
        String token = jwtService.generateToken(user);
        Claims claims = jwtService.decryptJwtToken(token);
        Assert.assertEquals(Long.toString(id), claims.getId());
    }
}
