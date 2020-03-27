package com.ascending.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ascending.service.FileService;
import com.ascending.service.FileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("dev")
public class AwsConfig {
    @Bean
    public FileService getFileService(){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
        FileService fileService = new FileServiceImpl(s3Client);
//        fileService.setBucketName("training-dan-1");
        return fileService;
    }
}
