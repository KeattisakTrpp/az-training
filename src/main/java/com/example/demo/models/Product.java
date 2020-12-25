package com.example.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@NoArgsConstructor
public class Product {
    @Id
    @Field(name = "product_id")
    private String productId;
    @Field(name = "product_name")
    private String productName;
    @Field(name = "total_budget")
    private Integer totalBudget;
    private Integer budget;

    public Product(String productName, Integer totalBudget, Integer budget) {
        this.productName = productName;
        this.totalBudget = totalBudget;
        this.budget = budget;
    }

}
