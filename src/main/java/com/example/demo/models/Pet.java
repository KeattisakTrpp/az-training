package com.example.demo.models;

import com.example.demo.PetType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
public class Pet {
    @Id
    private String id;
    private String name;
    private int age;
    @DBRef
    private List<Product> productList = new ArrayList<>();
    private PetType type = PetType.DOG;

    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
