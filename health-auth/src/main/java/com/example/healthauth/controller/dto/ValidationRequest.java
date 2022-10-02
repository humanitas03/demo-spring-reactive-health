package com.example.healthauth.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ValidationRequest(
    String token
) {

    @Override
    public String token() {
        return token;
    }
}
