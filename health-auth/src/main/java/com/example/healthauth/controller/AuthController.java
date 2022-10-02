package com.example.healthauth.controller;

import com.auth0.jwk.JwkException;
import com.example.healthauth.controller.dto.TokenRequest;
import com.example.healthauth.controller.dto.TokenResponse;
import com.example.healthauth.controller.dto.ValidationRequest;
import com.example.healthauth.controller.dto.ValidationResponse;
import com.example.healthauth.service.AuthService;
import com.example.healthauth.service.dto.PayloadAuthorities;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token/issue")
    public Mono<TokenResponse> getToken(@RequestBody TokenRequest request) {
        return authService.getToken(request);
    }

    @PostMapping("/token/verify")
    public Mono<ValidationResponse> verify(@RequestBody ValidationRequest request) {
        log.info("token :: {}", request.token());
        return authService.validateToken(request.token());
    }
}
