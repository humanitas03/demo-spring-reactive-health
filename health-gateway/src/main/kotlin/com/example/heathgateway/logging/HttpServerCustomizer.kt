package com.example.heathgateway.logging

import io.netty.handler.logging.LogLevel
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.stereotype.Component
import reactor.netty.http.server.HttpServer
import reactor.netty.transport.logging.AdvancedByteBufFormat

@Component
class HttpServerCustomizer : NettyServerCustomizer {
    override fun apply(httpServer: HttpServer): HttpServer = httpServer.compress(false)
        .wiretap("gateway-server", LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL)

}