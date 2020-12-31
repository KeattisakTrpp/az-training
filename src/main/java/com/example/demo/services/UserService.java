package com.example.demo.services;

import com.example.demo.models.UsedBudget;
import com.example.demo.security.UserPayload;
import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;


    public List<UserModel> getAllUser() {
        return userRepository.findAll();
    }

    public UserModel getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserModel signup(UserModel newUser) {
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            if(userRepository.findByCitizenId(newUser.getCitizenId()).isEmpty()) {
                newUser.setPassword(encoder.encode(newUser.getPassword()));
                return userRepository.save(newUser);
            }
            throw new RuntimeException("Citizen id is invalid");
        }
        throw new RuntimeException("Email is invalid");
    }

    public UserPayload login(String email, String password) {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), password));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Username or password incorrect");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String jwt = jwtToken.generateToken(userDetails);

        return new UserPayload(user, jwt);
    }

    public UserModel buyProduct(String id, List<String> productIdList, String petId) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        // set product expiration
        Date today = new Date();
        Calendar expDate = Calendar.getInstance();
        expDate.setTime(today);
        for(String productId: productIdList ) {
            ProductDetails product = productDetailsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
            expDate.add(Calendar.YEAR, product.getDuration());
            purchaseRepository.save(new Purchase(user.getId(), product, pet));

            Product purchasedProduct = new Product(product.getName(), product.getOpd(), product.getAccident(), product.getPrice(), expDate.getTime());
            Budget budget = new Budget(productRepository.save(purchasedProduct));
            budget.setUsedBudget(new UsedBudget());
            pet.getProductList().add(budgetRepository.save(budget));
        }
        petRepository.save(pet);
        return userRepository.findById(id).get();
    }

    public Pet addPet(String id, Pet pet) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.getPets().add(petRepository.save(pet));
        userRepository.save(user);
        return pet;
    }

    public List<Pet> getPets(String id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPets();
    }

    public Pet claim(String petId, int amount, String budgetId, String userId, ClaimType claimType) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(() -> new RuntimeException("Product not found"));
        Product product = budget.getProduct();
            int usedBudget = budget.getUsedBudget().getOpd();

            if (claimType.getValue().equalsIgnoreCase("ACCIDENT")) {
                usedBudget = budget.getUsedBudget().getAccident();
                if(usedBudget < product.getAccident()) {
                    if(usedBudget + amount > product.getAccident()) {
                        claimRepository.save(new Claim(userId , product, pet , product.getAccident() - usedBudget ,ClaimType.ACCIDENT ,ClaimStatus.PENDING));
                        budget.getUsedBudget().setAccident(budget.getProduct().getAccident());
                    } else {
                        claimRepository.save(new Claim(userId ,product, pet , amount ,ClaimType.ACCIDENT ,ClaimStatus.PENDING));
                        budget.getUsedBudget().setAccident(budget.getUsedBudget().getAccident() + amount);
                    }
                    budgetRepository.save(budget);
                    petRepository.save(pet);
                    return petRepository.findById(pet.getId()).get();
                }
                throw new RuntimeException("You have no accident budget left");
            }

            if(usedBudget < product.getOpd()) {
                if(usedBudget + amount > product.getOpd()) {
                    claimRepository.save(new Claim(userId , product, pet , product.getOpd() - usedBudget ,ClaimType.OPD ,ClaimStatus.PENDING));
                    budget.getUsedBudget().setOpd(budget.getUsedBudget().getOpd());
                    System.out.println("OPD OVER");
                } else {
                    claimRepository.save(new Claim(userId , product, pet , amount ,ClaimType.OPD ,ClaimStatus.PENDING));
                    budget.getUsedBudget().setOpd(budget.getUsedBudget().getOpd() + amount);
                    System.out.println("OPD LESS");
                }
                budgetRepository.save(budget);
                petRepository.save(pet);
                return petRepository.findById(pet.getId()).get();
            }
            throw new RuntimeException("You have no opd budget left");
    }
}
