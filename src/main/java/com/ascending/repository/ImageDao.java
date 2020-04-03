package com.ascending.repository;

import com.ascending.model.Image;

import java.util.List;

public interface ImageDao {
    Image saveOrUpdate(Image image);
//    Image update(Image image);
    Boolean delete(Image image);
//    List<Image> getImages();
    List<Image> getImagesByUserId(Long userId);
    List<Image> getImagesByBucket(String bucketName);
}
