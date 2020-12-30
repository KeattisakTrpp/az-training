package com.example.demo.repositories;

import com.example.demo.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByCitizenId(String citizenId);
}
