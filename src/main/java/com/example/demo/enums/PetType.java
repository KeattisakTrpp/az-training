package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PetType {
    CAT("CAT"),
    DOG("DOG");

    private String type;

    PetType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getValue() {
        return this.type;
    }
}
