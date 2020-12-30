package com.example.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
public class Purchase {
    @Id
    private String id;
    private String userId;
    @DBRef
    private ProductDetails productDetails;
    private Date purchaseDate = new Date();

    public Purchase(String userId, ProductDetails productDetails) {
        this.userId = userId;
        this.productDetails = productDetails;
    }
}
