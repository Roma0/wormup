package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Image;
import com.ascending.repository.ImageDao;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class ImageServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageDao imageDao;

    private MultipartFile multipartFile;
    private String extension;
    private String fileBaseName;
    private Image image;

    @Before
    public void setUp(){
        logger.debug("Setup before testing...");
        multipartFile = new MockMultipartFile("file", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        fileBaseName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
    }

    @After
    public void tearDown(){
        imageDao.delete(image);
        logger.debug("TearDown after testing...");
    }

    @Test
    public void saveUuidImage(){
        logger.debug("Testing saveUuidImage()...");
        image = imageService.saveUuidImage(multipartFile);
//        logger.debug(">>>>>>>>Testing... ", image.getUrl().toString());
        Assert.assertTrue(image.getS3Key().startsWith(fileBaseName));
        Assert.assertTrue(image.getS3Key().endsWith(extension));
        Assert.assertTrue(image.getUrl().toString().contains(image.getS3Key()));
        logger.debug(">>>>>> The URL is ", image.getUrl().toString());
    }
}
