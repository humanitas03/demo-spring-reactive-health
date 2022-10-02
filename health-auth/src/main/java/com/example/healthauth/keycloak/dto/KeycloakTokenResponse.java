package com.example.healthauth.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KeycloakTokenResponse(
    @JsonProperty("access_token")
    String accessToken,
    @JsonProperty("expires_in")
    Long expiresIn,
    @JsonProperty("refresh_expires_in")
    Long refreshExpiresIn,
    @JsonProperty("refresh_token")
    String refreshToken,
    @JsonProperty("token_type")
    String tokenType
) {

    @Override
    public String accessToken() {
        return accessToken;
    }

    @Override
    public Long expiresIn() {
        return expiresIn;
    }

    @Override
    public Long refreshExpiresIn() {
        return refreshExpiresIn;
    }

    @Override
    public String tokenType() {
        return tokenType;
    }
}
