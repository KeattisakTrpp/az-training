package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PetGender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private String type;

    PetGender(String type) {
        this.type = type;
    }

    @JsonValue
    public String getValue() {
        return this.type;
    }
}
