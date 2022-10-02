package com.example.healthauth.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidationResponse(
    String username,
    @JsonProperty("is_accessible")
    boolean isAccessible
) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public boolean isAccessible() {
        return isAccessible;
    }
}
