package com.ascending.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ascending.ApplicationBootstrap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class FileServiceTest {
    @Autowired
    FileService fileService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String awsBucket = "training-dan-1";
    private MultipartFile multipartFile = new MockMultipartFile("file", "test.jpg",
            "image/jpeg", "test image content".getBytes());

    //before
    //set environment and sanity check
    //pass bucket variable in instance level
    //File has to be relative path

    //after
    //delete file in S3

    @Before
    public void setUp(){
        try {
            fileService.uploadObject(awsBucket, multipartFile);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }


    @Test
    public void uploadObjectTest(){

        //assertion for mockito
        Mockito.verify(fileService.getS3Client(), times(1))
                .putObject(eq(awsBucket),
                        eq(multipartFile.getOriginalFilename()),
                        any(InputStream.class),
                        any(ObjectMetadata.class));
        logger.debug(String.format("The file of '%s' was uploaded", multipartFile.getName()) );
        //assertion for real network
//        Assert.assertNotNull(fileService.getObjectUrl(file.getName()));

    }

//To test throw error

    @Test
    public void getObjectPublicUrl(){
        logger.debug(String.format("Get the public url of the file '%s'", multipartFile.getOriginalFilename()));
        Mockito.verify(fileService.getS3Client(), times(2))
                .getUrl(eq(awsBucket),
                        eq(multipartFile.getOriginalFilename()));
    }

//    @Test
//    public void listObjects(){
//        Mockito.verify(fileService.getS3Client(), times(3))
//                .listObjects(eq(awsBucket));
//        logger.debug(String.format("The files on the bucket '%s' are %s", awsBucket,
//                fileService.listObjects(awsBucket)));
//    }
}
