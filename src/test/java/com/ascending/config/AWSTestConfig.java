package com.ascending.config;

import com.amazonaws.services.s3.AmazonS3;
import com.ascending.service.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("unit")
public class AWSTestConfig {
    @Bean
    public FileService getFileService(){
        AmazonS3 s3Client = mock(AmazonS3.class);
        FileService fileService = new FileService(s3Client);
        fileService.setBucketName("training-dan-1");
        return fileService;
    }
}
