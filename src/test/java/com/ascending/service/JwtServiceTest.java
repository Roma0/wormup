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

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class JwtServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired JwtService jwtService;
    @Autowired UserService userService;
    @Autowired UserDao userDao;

    private User user;
    private Long id;
    private final String name = "dwang";
    private final String firstName = "David";
    private final String lastName = "Wang";
    private final String email = "dwang@training.ascendingdc.com";
    private final String password = "123456789";
    private String token;

    @Before
    public void setUp(){
        user = new User(name, password, firstName,lastName, email);
        user = userService.getUserByEmail(email);
        id = user.getId();

//        id = userService.save(user).getId();
//        assert ( id != null);
        logger.debug("User is setup before testing,");
    }

//    @After
//    public void tearDown(){
//        userDao.delete(user);
//        assert (userDao.findById(id) == null);
//        logger.debug("User is teared down from the database after testing.");
//    }

    @Test
    public void generateToken(){
        Map<String, Object> token = new HashMap<>();
        token = jwtService.generateToken(user);
        Assert.assertEquals(3, token.get("token").toString().split("\\.").length);
    }

    @Test
    public void decryptJwtToken(){
        Map<String, Object> token = jwtService.generateToken(user);
        Claims claims = jwtService.decryptJwtToken(token);
        Assert.assertEquals(Long.toString(id), claims.getId());
    }
}
