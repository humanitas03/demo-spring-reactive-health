package com.example.heathgateway.client

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthClient(
    private val defaultWebclient: WebClient,
    @Value("\${auth.url}")
    private val authBaseUrl: String,
    @Value("\${auth.path}")
    private val authTokenVerifyPath: String,
) {

    suspend fun verifyToken(jwt: String): TokenVerifyResponse = defaultWebclient.post().uri(
        "$authBaseUrl$authTokenVerifyPath"
    ).contentType(MediaType.APPLICATION_JSON)
        .bodyValue(TokenVerifyRequest(jwt))
        .retrieve()
        .bodyToMono(TokenVerifyResponse::class.java)
        .onErrorMap {
            throw RuntimeException("Verify Token Client Error", it)
        }
        .awaitSingle()
}

data class TokenVerifyRequest(
    val token: String,
)

data class TokenVerifyResponse(
    @JsonProperty("is_accessible")
    val isAccessible: Boolean,
    val username: String,
)

data class AuthTokenResponse(
    val type: String,
    val token: String,
)
