package com.example.demo.controllers;

import com.example.demo.models.Claim;
import com.example.demo.models.Purchase;
import com.example.demo.repositories.ClaimRepository;
import com.example.demo.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/history/")
public class HistoryController {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping("{userId}/claim")
    public List<Claim> getAllClaim(@PathVariable String userId) {
        return claimRepository.findAllByUserId(userId);
    }

    @GetMapping("{userId}/purchase")
    public List<Purchase> getAllPurchase(@PathVariable String userId) {
        return purchaseRepository.findAllByUserId(userId);
    }
}
