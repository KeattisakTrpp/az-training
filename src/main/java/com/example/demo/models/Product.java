package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private Integer opd;
    private Integer accident;
    private Integer price;
    private Date buyDate = new Date();
    private Date expDate;

    public Product(String name, Integer opd, Integer accident, Integer price, Date expDate) {
        this.name = name;
        this.opd = opd;
        this.accident = accident;
        this.price = price;
        this.expDate = expDate;
    }
}
