package com.example.healthauth.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
public class WebClientConfiguration {

    @Value("${keycloak.base-url}")
    private String keycloakBaseUrl;

    @Bean
    @Primary
    public WebClient webclient() {
        return WebClient.builder()
            .clientConnector(
                new ReactorClientHttpConnector(
                    HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15_000)
                        .doOnConnected( (it) -> {
                            it.addHandlerLast(new ReadTimeoutHandler(8));
                            it.addHandlerLast(new WriteTimeoutHandler(8));
                            })
                        .wiretap("reactor.netty.http.client.HttpClient",
                            LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL)
                        .compress(false)
                )
            )
            .baseUrl(keycloakBaseUrl)
            .build();
    }
}
