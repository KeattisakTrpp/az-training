package com.example.demo.repositories;

import com.example.demo.models.Claim;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends MongoRepository<Claim, String> {
    List<Claim> findAllByUserId(String userId);
}
