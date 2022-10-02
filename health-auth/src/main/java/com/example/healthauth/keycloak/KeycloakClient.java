package com.example.healthauth.keycloak;

import com.example.healthauth.keycloak.dto.KeycloakTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient {

    @Value("${keycloak.path.token}")
    private String tokenPath;


    @Value("${keycloak.scope}")
    private String clientScope;

    @Value("${keycloak.client-id}")
    private String clientId;

    private final WebClient webclient;

    public Mono<KeycloakTokenResponse> getUserToken(String username, String password) {
        return webclient
            .post()
            .uri(tokenPath)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromFormData("grant_type", "password")
                    .with("username", username)
                    .with("password", password)
                    .with("scope",clientScope)
                    .with("client_id", clientId)
            )
            .retrieve()
            .bodyToMono(KeycloakTokenResponse.class)
            .onErrorMap((err)->{
                log.error("error");
                throw new RuntimeException("webclient error", err);
            });
    }

}
