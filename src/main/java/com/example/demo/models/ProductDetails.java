package com.example.demo.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@NoArgsConstructor
public class ProductDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private Integer opd;
    private Integer accident;
    private Integer price;
    private int duration; // year
    private String description;

    public ProductDetails(String name, Integer opd, Integer accident, Integer price, int duration, String description) {
        this.name = name;
        this.opd = opd;
        this.accident = accident;
        this.price = price;
        this.duration = duration;
        this.description = description;
    }
}
