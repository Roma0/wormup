package com.ascending.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.ascending.service.FileService;
import com.ascending.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("dev")
public class AwsConfig {

    @Bean
    public FileService getFileService(){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
        FileService fileService = new FileService(s3Client);
//        fileService.setBucketName("training-dan-1");
        return fileService;
    }

    @Bean
    public MessageService messageService(){
        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard().build();
        MessageService messageService = new MessageService(sqsClient);
        return messageService;
    }
}
