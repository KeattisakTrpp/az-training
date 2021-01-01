package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
@NoArgsConstructor
public class UserModel {
    @Id
    private String id;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private String email;
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    private String citizenId;
    private String address = "";
    @NonNull
    private String phone;

    @DBRef
    private List<Pet> pets = new ArrayList<>();

    public UserModel(@NonNull String firstname, @NonNull String lastname, @NonNull String email, @NonNull String password, @NonNull String citizenId, @NonNull String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.citizenId = citizenId;
        this.phone = phone;
    }

    public UserModel(@NonNull String firstname, @NonNull String lastname, @NonNull String email, @NonNull String password, @NonNull String citizenId, String address, @NonNull String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.citizenId = citizenId;
        this.address = address;
        this.phone = phone;
    }
}
