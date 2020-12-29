package com.example.demo.services;

import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User signup(User newUser) {
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            if(userRepository.findByCitizenId(newUser.getCitizenId()).isEmpty()) {
                newUser.setPassword(encoder.encode(newUser.getPassword()));
                return userRepository.save(newUser);
            }
            throw new RuntimeException("Citizen id is invalid");
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
                Budget budget = new Budget(product);
                pet.getProductList().add(budgetRepository.save(budget));
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

    public Pet claim(String petId, int amount, String productId, String userId, ClaimType claimType) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        for(Budget budget: pet.getProductList()) {
            Product product = budget.getProduct();
            if(product.getId().equalsIgnoreCase(productId)) {
                int usedBudget = budget.getUsedBudget().getOpd();
                if (claimType.getValue().equalsIgnoreCase("ACCIDENT")) {
                    usedBudget = budget.getUsedBudget().getAccident();
                    if(usedBudget <= product.getAccident()) {
                        if(usedBudget + amount > product.getAccident()) {
                            claimRepository.save(new Claim(userId ,pet , product.getAccident() - usedBudget ,ClaimType.ACCIDENT ,ClaimStatus.PENDING));
                        } else {
                            claimRepository.save(new Claim(userId ,pet , amount ,ClaimType.ACCIDENT ,ClaimStatus.PENDING));
                        }
                        petRepository.save(pet);
                        return pet;
                    }
                }

                if(usedBudget <= product.getOpd()) {
                    if(usedBudget + amount > product.getOpd()) {
                        claimRepository.save(new Claim(userId ,pet , product.getOpd() - usedBudget ,ClaimType.OPD ,ClaimStatus.PENDING));
                    } else {
                        claimRepository.save(new Claim(userId ,pet , amount ,ClaimType.OPD ,ClaimStatus.PENDING));
                    }
                    petRepository.save(pet);
                    return pet;
                }
                throw new RuntimeException("You have no budget left");
            }
        }
        throw new RuntimeException("Product not found");
    }
}
