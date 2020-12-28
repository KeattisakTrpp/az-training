package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    private String id;
    private Date date;
    @DBRef
    private Product product;
    @DBRef
    private Pet pet;
    private int amount;
//    private String type;
//    private String status;

}
