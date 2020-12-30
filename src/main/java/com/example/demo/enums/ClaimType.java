package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClaimType {
    OPD("OPD"),
    ACCIDENT("ACCIDENT");

    private String type;

    ClaimType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getValue() {
        return this.type;
    }
}
