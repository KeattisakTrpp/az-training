package com.example.demo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PetType {
    CAT("Cat"),
    DOG("Dog");

    private String type;

    PetType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getValue() {
        return this.type;
    }
}
