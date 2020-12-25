package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User addUser(User newUser) {
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);
        }
        throw new RuntimeException("Email is invalid");
    }

    public User login(String email, String password) throws RuntimeException {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isEmpty()) {
            if(encoder.matches(password, user.get().getPassword())) {
                return user.get();
            }
            throw new RuntimeException("Username or password incorrect");
        }
        throw new RuntimeException("User not found");
    }

    public User addProduct(String id, Product product) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.getProductList().add(product);
        return userRepository.save(user);
    }
}
