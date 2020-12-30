package com.example.demo.controllers;

import com.example.demo.models.ProductDetails;
import com.example.demo.repositories.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @PostMapping
    public ProductDetails addProduct(@RequestBody ProductDetails product) {
        if(productDetailsRepository.findByName(product.getName()).isPresent()){
           throw new RuntimeException("Product is already exist");
        }
        return productDetailsRepository.save(product);
    }

    @GetMapping
    public List<ProductDetails> getAllProduct() {
        return productDetailsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(productDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found")));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
