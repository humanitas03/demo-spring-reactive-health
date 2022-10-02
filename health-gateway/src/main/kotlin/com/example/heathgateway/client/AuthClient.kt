package com.example.heathgateway.client

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthClient(
    private val defaultWebclient: WebClient
){

    suspend fun verifyToken(jwt: String) : AuthTokenResponse = defaultWebclient.
}

data class AuthTokenResponse(
    val type: String,
    val token: String,
)