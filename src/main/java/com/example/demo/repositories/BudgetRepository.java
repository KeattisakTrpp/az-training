package com.example.demo.repositories;

import com.example.demo.models.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BudgetRepository extends MongoRepository<Budget, String> {
}
