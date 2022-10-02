package com.example.heathgateway.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.http.codec.LoggingCodecSupport
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class WebclientConfiguration {

    @Bean
    fun defaultWebclient(): WebClient {

        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer: ClientCodecConfigurer ->
                configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50)
            }
            .build()

        exchangeStrategies
            .messageWriters().stream()
            .filter { obj: HttpMessageWriter<*>? ->
                LoggingCodecSupport::class.java.isInstance(
                    obj
                )
            }
            .forEach { writer: HttpMessageWriter<*> ->
                (writer as LoggingCodecSupport).isEnableLoggingRequestDetails = true
            }

        val provider = ConnectionProvider.builder("webclient-connection-pool")
            .maxConnections(500)
            .pendingAcquireTimeout(Duration.ofMillis(0))
            .pendingAcquireMaxCount(-1)
            .maxIdleTime(Duration.ofMillis(8000L))
            .maxLifeTime(Duration.ofMillis(8000L))
            .build()

        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create(provider)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15_000)
                        .doOnConnected {
                            it.addHandlerLast(ReadTimeoutHandler(8))
                            it.addHandlerLast(WriteTimeoutHandler(8))
                        }
                        .wiretap(true)
                )
            )
            .exchangeStrategies(exchangeStrategies)
            .build()
    }
}
