package com.example.healthauth.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TokenRequest(
    @JsonProperty("user_id")
    String userId,
    String password
) {

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public String password() {
        return password;
    }
}
