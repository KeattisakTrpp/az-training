package com.example.demo.request;

import com.example.demo.enums.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequest {
    private int amount;
    private String budgetId;
    private String petId;
    private ClaimType claimType;
}
