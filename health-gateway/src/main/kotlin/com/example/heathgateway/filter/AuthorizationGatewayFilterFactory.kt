package com.example.heathgateway.filter

import com.example.heathgateway.service.AuthService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class AuthorizationGatewayFilterFactory(
    private val authService: AuthService
) : AbstractGatewayFilterFactory<Any>() {
    override fun apply(config: Any?): GatewayFilter = GatewayFilter { ex, chain ->
        log.info("==========AuthorizationGatewayFilterFactory=======================")
        val preRequest = ex.request
        val jsonWebToken = ex.request.headers["x-health-authorization"]?.get(0) ?: throw RuntimeException("authorization should not be null")
        mono {
            log.info("========== MONO AuthorizationGatewayFilterFactory START=======================")
            val authResult = authService.verifyToken(jsonWebToken)
            if (authResult.isAccessible) {
                preRequest.mutate().headers {
                    it.add("x-user-id", authResult.username)
                }.build()

                log.info("==========MONO AuthorizationGatewayFilterFactory  END=======================")
                chain.filter(
                    ex.mutate().request(preRequest).build()
                ).awaitSingleOrNull()
            } else {
                throw RuntimeException("해당 자원 접근에 권한이 없습니다.")
            }
        }
    }
}
