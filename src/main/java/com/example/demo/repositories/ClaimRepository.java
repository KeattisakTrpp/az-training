package com.example.demo.repositories;

import com.example.demo.models.Claim;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClaimRepository extends MongoRepository<Claim, String> {
    Optional<Claim> getClaimByUserId(String userId);
}
