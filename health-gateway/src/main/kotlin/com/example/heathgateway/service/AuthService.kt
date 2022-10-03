package com.example.heathgateway.service

import com.example.heathgateway.client.AuthClient
import com.example.heathgateway.client.TokenVerifyResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authClient: AuthClient,
) {

    suspend fun verifyToken(jwt: String): TokenVerifyResponse {
        val result = authClient.verifyToken(jwt)

        if (result.isAccessible) {
            throw RuntimeException("")
        }

        return result
    }
}
