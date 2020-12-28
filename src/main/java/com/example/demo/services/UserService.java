package com.example.demo.services;

import com.example.demo.models.Pet;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repositories.PetRepository;
import com.example.demo.repositories.ProductRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public User addUser(User newUser) {
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);
        }
        throw new RuntimeException("Email is invalid");
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if(encoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Username or password incorrect");

    }

    public User buyProduct(String id, String productId, String petId) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        user.getProductList().add(product);
        userRepository.save(user);
        for(Pet p: user.getPets()) {
            if(p.getName().equalsIgnoreCase(pet.getName())) {
                pet.getProductList().add(product);
                petRepository.save(pet);
                break;
            }
        }
        return userRepository.findById(id).get();

    }

    public User addPet(String id, Pet pet) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.getPets().add(pet);
        return userRepository.save(user);
    }

    public List<Pet> getPets(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPets();
    }

    public User claim(String id, int amount, String productId) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getProductList().contains(productId)) {
            for(Product p: user.getProductList()) {
                if(p.getId().equalsIgnoreCase(productId)) {
                    int usedBudget = p.getUsedBudget();

                    if(usedBudget + amount <= p.getTotalBudget()) {
                        p.setUsedBudget(usedBudget + amount);
                        userRepository.save(user);
                        return user;
                    }
                    // another case is budget has not enough for full claiming
                    // waiting for discuss
                }
            }
            throw new RuntimeException("Your budget is max");
        }
        throw new RuntimeException("Product not found");
    }
}
