package com.ascending.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ascending.ApplicationBootstrap;
import org.junit.After;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class FileServiceTest {

    @Autowired
    FileService fileService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String awsBucket = "training-dan-1";
    private MultipartFile multipartFile;
    private String s3key;

    //before
    //set environment and sanity check
    //pass bucket variable in instance level
    //File has to be relative path

    //after
    //delete file in S3

    @Before
    public void setUp(){
        logger.info("Start testing ...");
        multipartFile = new MockMultipartFile("file", "test.jpg",
                "image/jpeg", "test image content".getBytes());
        s3key = "test_" + UUID.randomUUID().toString() + ".jpg";
    }

    @After
    public void tearDown(){
        logger.info("End testing ...");
        Mockito.reset(fileService.getS3Client());
    }


    @Test
    public void uploadObjectTest(){
        try {
            fileService.uploadObject(awsBucket, multipartFile, s3key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        //assertion for mockito
        Mockito.verify(fileService.getS3Client(), times(1))
                .putObject(eq(awsBucket),
                        eq(s3key),
                        any(InputStream.class),
                        any(ObjectMetadata.class));
        logger.debug(String.format("The file of '%s' was uploaded", multipartFile.getName()) );
        //assertion for real network
//        Assert.assertNotNull(fileService.getObjectUrl(file.getName()));

    }

//To test throw error

    @Test
    public void getObjectPublicUrl()throws MalformedURLException {
        URL url = new URL("http","localhost","fakeUrl");
        try {
            fileService.getObjectPublicUrl(awsBucket,s3key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        Mockito.verify(fileService.getS3Client(), times(1))
                .getUrl(eq(awsBucket),
                        eq(s3key));
        logger.debug(String.format("Get the public url of the file '%s'", url));
    }

//    @Test
//    public void listObjects(){
//        String fileList = null;
//        fileList = fileService.listObjects(awsBucket);
//        Mockito.verify(fileService.getS3Client(), times(1))
//                .listObjects(eq(awsBucket));
//        logger.debug(String.format("The files on the bucket '%s' are %s", awsBucket,
//                fileList));
//    }
}
