package com.example.demo.models;

import com.example.demo.enums.PetGender;
import com.example.demo.enums.PetType;
import com.mongodb.lang.NonNull;
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
    @NonNull
    private String name;
    @NonNull
    private int age;
    @DBRef
    private List<Budget> productList = new ArrayList<>();
    @NonNull
    private PetType type = PetType.DOG;
    @NonNull
    private String breed;
    private PetGender gender = PetGender.MALE;

    public Pet(@NonNull String name, int age, @NonNull PetType type, @NonNull String breed, PetGender gender) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.breed = breed;
        this.gender = gender;
    }
}
