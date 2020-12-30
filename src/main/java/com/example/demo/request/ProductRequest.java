package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private Integer opd;
    private Integer accident;
    private Integer price;
    private Date buyDate = new Date();
    private int duration;


}
