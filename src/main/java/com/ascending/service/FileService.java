package com.ascending.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


public class FileService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private AmazonS3 s3Client;
    private String awsBucket = System.getProperty("aws.bucket");

    public FileService(AmazonS3 s3Client){
        this.s3Client = s3Client;
    }

    public AmazonS3 getS3Client() {
        return s3Client;
    }


    //upload object
    public URL uploadObject(MultipartFile f, String s3Key) throws IOException{
        return uploadObject(awsBucket, f, s3Key);
    }

    public URL uploadObject(String awsBucket, MultipartFile f, String s3Key) throws IOException{
        if (f == null) {
            throw new IOException("File must not be null.");
        }

        try {
            if(s3Client.doesObjectExist(awsBucket, s3Key)){
                logger.info(String.format("The file '%s' with key '%s' is exists in the bucket '%s'",
                        f.getOriginalFilename(), s3Key, awsBucket));
                return null;
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(f.getContentType());
            objectMetadata.setContentLength(f.getSize());
            s3Client.putObject(awsBucket, s3Key, f.getInputStream(),objectMetadata);
            logger.info(String.format("The file name=%s, size=%s, type=%s was upload to bucket %s.",
                    f.getOriginalFilename(), f.getSize(), f.getContentType(), awsBucket));
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
        return getObjectPublicUrl(awsBucket, s3Key);
    }

    //get objectUrl
    public URL getObjectPublicUrl(String s3Key){
        return getObjectPublicUrl(awsBucket, s3Key);
    }

    public URL getObjectPublicUrl(String awsBucket, String key){
        //For public Url
            return s3Client.getUrl(awsBucket, key);
    }

    //For private Url
    public URL getObjectPrivateUrl(String key){
        return getObjectPublicUrl(awsBucket, key);
    }

    public URL getObjectPrivateUrl(String awsBucket, String key){
        //Set the presigned Url to expire after one hour
        LocalDateTime expiration = LocalDateTime.now().plusHours(24);
        //Generate presigned Url
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(awsBucket, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }


    //create bucket

    //list bucket

    //delete bucket

    //list object
//    public String listObjects(String awsBucket){
//        try {
//            return s3Client.listObjects(awsBucket).getObjectSummaries().toString();
//        }catch (Exception e){
//            logger.error(e.getMessage());
//            return null;
//        }
//    }

    //delete object


}
