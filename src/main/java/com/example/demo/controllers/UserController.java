package com.example.demo.controllers;

import com.example.demo.models.Pet;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.PetRepository;
import com.example.demo.request.BuyProductRequest;
import com.example.demo.request.ClaimRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PetRepository petRepository;

    @GetMapping()
    public List<UserModel> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
        try {
            Pet pet = request.getPet();
            request.getUser().getPets().add(petRepository.save(pet));
            return ResponseEntity.ok(userService.signup(request.getUser()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        try {
            return ResponseEntity.ok(userService.login(user.getEmail(), user.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/buy")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @RequestBody BuyProductRequest request) {
        try {
            return ResponseEntity.ok(userService.buyProduct(userId, request.getProductId(), request.getPetId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/pet")
    public ResponseEntity<?> addPet(@PathVariable String id, @RequestBody Pet pet) {
        try {
            return ResponseEntity.ok(userService.addPet(id, pet));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/pet")
    public ResponseEntity<?> getPet(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.getPets(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/claim")
    public ResponseEntity<?> claim(@PathVariable String userId, @RequestBody ClaimRequest request) {
        try {
            return ResponseEntity.ok(userService.claim(request.getPetId(), request.getAmount(), request.getBudgetId(), userId, request.getClaimType()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
