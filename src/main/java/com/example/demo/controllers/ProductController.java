package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        System.out.println(id);
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
