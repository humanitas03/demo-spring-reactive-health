package com.example.healthauth.service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.healthauth.controller.dto.TokenRequest;
import com.example.healthauth.controller.dto.TokenResponse;
import com.example.healthauth.controller.dto.TokenType;
import com.example.healthauth.controller.dto.ValidationResponse;
import com.example.healthauth.keycloak.KeycloakClient;
import com.example.healthauth.service.dto.PayloadAuthorities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final KeycloakClient keycloakClient;

    private final ObjectMapper objectMapper;

    @Value("${keycloak.base-url}")
    private String baseUrl;

    @Value("${keycloak.path.certs}")
    private String certsPath;

    public Mono<TokenResponse> getToken(TokenRequest request) {
        return keycloakClient
            .getUserToken(request.userId(), request.password())
            .map((kc) -> new TokenResponse(
                TokenType.PASSWORD,
                kc.accessToken()
            ));
    }

    public Mono<ValidationResponse> validateToken(String jwtToken) {
        return Mono.fromCallable(()->{
            var jwkUrl = baseUrl + certsPath;
                try {
                    validateJwtSignature(jwtToken, jwkUrl);
                    return getAuthorization(jwtToken);
                } catch (JwkException e) {
                    e.printStackTrace();
                    throw new RuntimeException("JwkException occured", e);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("MalformedURLException occured", e);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new RuntimeException("JsonProcessingException", e);
                }
            })
            .map(it-> new ValidationResponse(
                it.username(),
                checkAccessible(it.authorities())
            ))
            .onErrorMap((error)->{
                log.error("error", error);
               throw new RuntimeException("validation token failed");
            });

    }

    private void validateJwtSignature(String jwtToken, String jwkUrl) throws JwkException, MalformedURLException {
        DecodedJWT jwt = JWT.decode(jwtToken);
        JwkProvider provider = new UrlJwkProvider(new URL(jwkUrl));
        Jwk jwk = provider.get(jwt.getKeyId());
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
        algorithm.verify(jwt);
    }

    private PayloadAuthorities getAuthorization(String jwtToken) throws JsonProcessingException {
        String[] chunks = jwtToken.split("\\.");
        var decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));

        String payload = new String(decoder.decode(chunks[1]));

        log.info("Header ::: {}",header);
        log.info("payload ::: {}",payload);

        return objectMapper.readValue(payload,PayloadAuthorities.class);
    }

    private Boolean checkAccessible(List<String> authority) {
        return authority.contains("health_app_admin");
    }
}
