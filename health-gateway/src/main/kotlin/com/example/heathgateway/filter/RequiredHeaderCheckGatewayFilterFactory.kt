package com.example.heathgateway.filter

import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class RequiredHeaderCheckGatewayFilterFactory : AbstractGatewayFilterFactory<Any>() {
    override fun apply(config: Any?): GatewayFilter = GatewayFilter { ex, chain ->
        val headerKeys = ex.request.headers.keys.map { it.lowercase() }
        if (!headerKeys.contains("x-health-authorization")) {
            throw RuntimeException("필수 헤더가 없습니다.")
        }

        chain.filter(ex)
    }
}
