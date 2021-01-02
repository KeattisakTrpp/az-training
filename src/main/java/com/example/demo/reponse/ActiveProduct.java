package com.example.demo.reponse;

import com.example.demo.models.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveProduct {
    private ProductDetails productDetails;
    private Date purchaseDate;
    private String petName;
}
