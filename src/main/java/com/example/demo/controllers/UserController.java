package com.example.demo.controllers;

import com.example.demo.models.Pet;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repositories.PetRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PetRepository petRepository;

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {
        try {
            // prepare for pet
//            newUser.getPets().forEach(p -> petRepository.save(p));
            User user = userService.addUser(newUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User record = userService.login(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/buy/{userId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @RequestBody Product product) {
        try {
            User user = userService.addProduct(userId, product);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
