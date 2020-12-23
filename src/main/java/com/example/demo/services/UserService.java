package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public User addUser(User newUser) {
        if(!userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);
        }
        return null;
    }
}
