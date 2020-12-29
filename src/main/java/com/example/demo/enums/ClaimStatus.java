package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClaimStatus {
    APPROVED("Approved"),
    PENDING("Pending");

    private String type;

    ClaimStatus(String type) {
        this.type = type;
    }

    @JsonValue
    public String getValue() {
        return this.type;
    }
}
