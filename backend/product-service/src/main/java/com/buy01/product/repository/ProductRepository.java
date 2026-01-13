package com.buy01.product.repository;

import com.buy01.product.dto.ProductResponseDTO;
import com.buy01.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> getProductByName(String productName); //later for search purposes
    List<Product> findAllProductsByUserId(String userId);
    int getQuantityByProductId(String productId);
    @Query("{ $and: [ " +
            "{ $or: [ { $where: '?0 == null' }, { 'name': { $regex: ?0, $options: 'i' } } ] }, " +
            "{ $or: [ { $where: '?1 == null' }, { 'price': { $gte: ?1 } } ] }, " +
            "{ $or: [ { $where: '?2 == null' }, { 'price': { $lte: ?2 } } ] } " +
            "] }")
    Page<Product> findAllByFilters(String name, Double minPrice, Double maxPrice, Pageable pageable);
}

