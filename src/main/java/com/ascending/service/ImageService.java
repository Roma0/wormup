package com.ascending.service;

import com.ascending.model.Image;
import com.ascending.model.User;
import com.ascending.repository.ImageDao;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
public class ImageService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
//    private String awsBucket = System.getProperty("aws.bucket");
    private String awsBucket = "training-dan-1";

    public Image saveUuidImage(MultipartFile f) throws ServiceException{
        if(f == null)throw new ServiceException("File must not be null!");
//        if (user == null )throw new ServiceException("User must not be null!");

        String extension = FilenameUtils.getExtension(f.getOriginalFilename());
        Image image = new Image();
        String s3Key = FilenameUtils.getBaseName(f.getOriginalFilename())
                + "_" + image.getUuid() + "." + extension;
        image.setS3Key(s3Key);
        image.setBucket(awsBucket);
        try {
            URL url = fileService.uploadObject(image.getBucket(),f, image.getS3Key());
            image.setUrl(url);
//            image.setUser(user);
            imageDao.saveOrUpdate(image);
            return image;
        }catch (IOException e){
            logger.error("Can't find image file in database.");
        }
        return null;
    }

//    public Boolean delete(Image image){
//        return imageDao.delete(image);
//    };
}
