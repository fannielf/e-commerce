package com.buy01.repository;

import com.buy01.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {
    List<Image> getImagesByProductId(String productId);

}
