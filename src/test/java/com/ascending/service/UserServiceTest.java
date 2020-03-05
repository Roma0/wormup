package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.User;
import com.ascending.repository.UserDao;
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
public class UserServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired UserService userService;
    @Autowired
    UserDao userDao;

    private User user;
    private final String name = "FX";
    private final String email = "fx@mail.com";
    private final String password = "pass123";
    private Long id;

    @Before
    public void setUp(){
        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.save(user);
        assert (user.getId() != null);
        logger.debug("SetUp successfully before testing! ");
        id = user.getId();
    }

    @After
    public void tearDown(){
        userDao.delete(user);
        assert (userDao.findById(id) == null);
        logger.debug(("TearDown successfully after testing!"));
    }

    @Test
    public void getUserByEmail(){
        Assert.assertEquals(id, userService.getUserByEmail(email).getId());
    }

    @Test
    public void getUserByCredentials(){
        Assert.assertEquals(id, userService.getUserByCredentials(email, password).getId());
    }
}
