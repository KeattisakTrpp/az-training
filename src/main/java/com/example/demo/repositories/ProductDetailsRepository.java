package com.example.demo.repositories;


import com.example.demo.models.ProductDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductDetailsRepository extends MongoRepository<ProductDetails, String> {
    Optional<ProductDetails> findByName(String name);
}
