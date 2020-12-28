package com.example.demo.request;

import com.example.demo.models.Pet;
import com.example.demo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private User user;
    private Pet pet;
}
