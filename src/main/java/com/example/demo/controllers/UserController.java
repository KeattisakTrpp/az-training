package com.example.demo.controllers;

import com.example.demo.models.Pet;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.request.BuyProductRequest;
import com.example.demo.request.ClaimRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.repositories.PetRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PetRepository petRepository;

    @GetMapping("/")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {
        try {
            Pet pet = request.getPet();
            System.out.println(petRepository.save(pet));
            request.getUser().getPets().add(petRepository.save(pet));
            User user = userService.addUser(request.getUser());
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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/buy")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @RequestBody BuyProductRequest request) {
        try {
            User user = userService.buyProduct(userId, request.getProductId(), request.getPetId());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/pet")
    public ResponseEntity<?> addPet(@PathVariable String id, @RequestBody Pet pet) {
        try {
            User user = userService.addPet(id, pet);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/pet")
    public ResponseEntity<?> getPet(@PathVariable String id) {
        try {
            List<Pet> pets = userService.getPets(id);
            return ResponseEntity.ok(pets);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/claim")
    public ResponseEntity<?> claim(@PathVariable String id, @RequestBody ClaimRequest request) {
        try {
            User user = userService.claim(id, request.getAmount(), request.getProductId());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pet/{id}")
    public ResponseEntity<?> sads(@PathVariable String id) {
        return ResponseEntity.ok(petRepository.findById(id).get());
    }
}
