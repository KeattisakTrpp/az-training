package com.example.demo.models;

import com.example.demo.UsedBudget;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Budget {
    @Id
    private String id;
    @DBRef
    private Product product;
    private UsedBudget usedBudget;

    public Budget(UsedBudget usedBudget) {
        this.usedBudget = usedBudget;
    }

    public Budget(Product product) {
        this.product = product;
    }
}
