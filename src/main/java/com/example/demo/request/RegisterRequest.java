package com.example.demo.request;

import com.example.demo.models.Pet;
import com.example.demo.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private UserModel user;
    private Pet pet;
}
