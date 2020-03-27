package com.ascending.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.inspector.model.InvalidInputException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class FileServiceImpl implements FileService{

    private Logger logger = LoggerFactory.getLogger(getClass());
    private AmazonS3 s3Client;

    public FileServiceImpl(AmazonS3 s3Client){
        this.s3Client = s3Client;
    }

    @Override
    public AmazonS3 getS3Client() {
        return s3Client;
    }

    //create bucket

    //list bucket

    //delete bucket

    //list object
    @Override
    public String listObjects(String awsBucket){
        try {
            return s3Client.listObjects(awsBucket).toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    //upload object
    @Override
    public String uploadObject(String awsBucket, MultipartFile f) throws IOException{
        if (f == null) throw new IOException("File must not be null.");
//        String awsBucket = System.getProperty("aws.bucket");
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(f.getContentType());
            objectMetadata.setContentLength(f.getSize());
            s3Client.putObject(awsBucket, f.getOriginalFilename(), f.getInputStream(),objectMetadata);
            logger.info(String.format("The file named %s was upload to bucket %s.", f.getOriginalFilename(), awsBucket));
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
        return getObjectPublicUrl(awsBucket, f.getOriginalFilename());
    }

    //get objectUrl
    @Override
    public String getObjectPublicUrl(String awsBucket, String key){
        //For public Url
        try {
            URL url = s3Client.getUrl(awsBucket, key);
            return url == null ? null : url.toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    //For private Url
    @Override
    public String getObjectPrivateUrl(String awsBucket, String key){
        String objectUrl = null;
        //Set the presigned Url to expire after one hour
        LocalDateTime expiration = LocalDateTime.now().plusHours(1);
        //Generate presigned Url
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(awsBucket, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));
        objectUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return objectUrl;
    }

    //delete object


}
