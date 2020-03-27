package com.ascending.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    AmazonS3 getS3Client();
    String listObjects(String awsBucket);
    String uploadObject(String awsBucket, MultipartFile f) throws IOException;
    String getObjectPublicUrl(String awsBucket, String key);
    String getObjectPrivateUrl(String awsBucket, String key);
}
