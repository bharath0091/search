package com.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class WebInvoker {

    private final WebClient webClient;
    private static final String TEXT = "text";
    private static final String JAVASCRIPT = "javascript";


    public WebInvoker(ObjectMapper mapper) {
        ExchangeStrategies strategies = ExchangeStrategies.builder().codecs(clientCodecConfigurer ->
                clientCodecConfigurer.customCodecs().decoder(
                        new Jackson2JsonDecoder(mapper,
                                new MimeType(TEXT, JAVASCRIPT, StandardCharsets.UTF_8)))
        ).build();

        webClient = org.springframework.web.reactive.function.client.WebClient.builder()
                        .exchangeStrategies(strategies)
                        .build();
    }

    public Mono<ClientResponse> GET(String url, int timeoutSecs) {
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .timeout(Duration.ofSeconds(timeoutSecs));
    }
}
