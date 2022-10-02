package com.example.healthauth.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PayloadAuthorities(
    @JsonProperty("user_name")
    String username,
    List<String> authorities
) {

    @Override
    public String username() {
        return username;
    }

    @Override
    public List<String> authorities() {
        return authorities;
    }
}
