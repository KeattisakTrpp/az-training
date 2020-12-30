package com.example.demo.models;

import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
public class Claim {
    @Id
    private String id;
    private Date date = new Date();
    @DBRef
    private Product product;
    @DBRef
    private Pet pet;
    private String userId;
    private int amount;
    private ClaimType type;
    private ClaimStatus status;

    public Claim(String userId, Product product, Pet pet, int amount, ClaimType type, ClaimStatus status) {
        this.userId = userId;
        this.product = product;
        this.pet = pet;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }
}
