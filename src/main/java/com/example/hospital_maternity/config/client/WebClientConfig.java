package com.example.hospital_maternity.config.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    private final int webclientConnectionTimeoutTime = 20000;

    // private String notificationApiBaseUrl = "http://localhost:8016";
    private final String notificationApiBaseUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api";


    @Bean
    public WebClient webClient() {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webclientConnectionTimeoutTime)
                .responseTimeout(Duration.ofMillis(webclientConnectionTimeoutTime))
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(webclientConnectionTimeoutTime, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(webclientConnectionTimeoutTime, TimeUnit.MILLISECONDS)));

        return WebClient.builder().baseUrl(notificationApiBaseUrl).clientConnector(new ReactorClientHttpConnector(httpClient)).filter(logRequest()).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_ATOM_XML_VALUE).build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logMethodAndUrl(request);
            logHeaders(request);
            return Mono.just(request);
        });
    }

    private void logHeaders(ClientRequest request) {
        request.headers().forEach((name, values) -> {
            values.forEach(value -> {
                logNameAndValuePair(name, value);
            });
        });
    }

    private void logNameAndValuePair(String name, String value) {
        LOGGER.info(name + " = " + value);
    }

    private void logMethodAndUrl(ClientRequest request) {
        LOGGER.info(request.method().name() + " to " + request.url());
    }
}
