package com.ascending.repository;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.User;
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
public class UserDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired UserDao userDao;
    private User user;
    private final String name = "FX";
    private final String email = "fx@mail.com";
    private final String password = "pass123";
    private Long id;


    @Before
    public void setUp(){
        user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        userDao.save(user);
        id = user.getId();
    }

    @After
    public void tearDown(){
        userDao.delete(user);
        assert (userDao.getUserByEmail(email) == null);
    }

    @Test
    public void saveUser(){
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void findById(){
        Assert.assertEquals(id, userDao.findById(id).getId());
    }

    @Test
    public void getUsrByCredentials(){
        Assert.assertEquals(id, userDao.getUserByCredentials(email, password).getId());
    }

}
