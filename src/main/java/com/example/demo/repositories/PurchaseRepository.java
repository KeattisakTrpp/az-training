package com.example.demo.repositories;

import com.example.demo.models.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    List<Purchase> findAllByUserId(String userId);
}
