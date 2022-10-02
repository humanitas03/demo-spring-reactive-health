package com.example.healthauth.keycloak.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KeycloakJwksResponse (
    String kid,
    String kty,
    String alg,
    String use,
    String n,
    String e
) {

    @Override
    public String kid() {
        return kid;
    }

    @Override
    public String kty() {
        return kty;
    }

    @Override
    public String alg() {
        return alg;
    }

    @Override
    public String use() {
        return use;
    }

    @Override
    public String n() {
        return n;
    }

    @Override
    public String e() {
        return e;
    }
}