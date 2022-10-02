package com.example.healthauth.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TokenResponse(
    TokenType type,
    String token
) {

    @Override
    public TokenType type() {
        return type;
    }

    @Override
    public String token() {
        return token;
    }
}
