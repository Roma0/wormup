package com.ascending.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private AmazonSQS sqsClient;

    public MessageService(AmazonSQS sqsClient){this.sqsClient = sqsClient;}

    public void sendMessage(){
        String myQueueUrl = "https://sqs.us-east-1.amazonaws.com/469638324168/training-dev";
//        System.out.println("Sending a message to MyQueue.\n");
//        amazonSQS.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));
        final SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.withQueueUrl(myQueueUrl).withMessageBody("msg");
        final SendMessageResult sendMessageResult = sqsClient.sendMessage(sendMessageRequest);
        final String sequenceNumber = sendMessageResult.getSequenceNumber();
        final String messageId = sendMessageResult.getMessageId();
        System.out.println("SendMessage succeed with messageId " + messageId + ", sequence number " + sequenceNumber + "\n");
    }

    public AmazonSQS getSqsClient() {
        return sqsClient;
    }

}
