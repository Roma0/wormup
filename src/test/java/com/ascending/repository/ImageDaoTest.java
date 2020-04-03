package com.ascending.repository;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Image;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class ImageDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;
    private Image image;
    private Long userId;
    private String bucket = "training-dan-1";

    @Before
    public void setUp() throws MalformedURLException{
        image = new Image();
        image.setBucket(bucket);
        image.setS3Key("testKey2");
        image.setUrl(new URL("http","localhost","testFile"));
        image.setUser(userDao.getUserByEmail("rhang@training.ascendingdc.com"));
        userId = imageDao.saveOrUpdate(image).getUser().getId();
        Assert.assertNotNull(image.getId());
    }

    @After
    public void tearDown(){
        imageDao.delete(image);

    }

    @Test
    public void getImagesByUserId(){
        logger.debug("Testing getImagesByUserId()...");
        List<Image> images = imageDao.getImagesByUserId(userId);
        Assert.assertEquals(2, images.size());
        Assert.assertEquals(userId, images.get(0).getUser().getId());
    }

    @Test
    public void getImagesByBucket(){
        logger.debug("Testing getImagesByBucket()...");
        List<Image> images = imageDao.getImagesByBucket(bucket);
        Assert.assertEquals(2, images.size());
        Assert.assertEquals(bucket, images.get(0).getBucket());
    }

}
