package com.search.service;

import com.search.domain.Album;
import com.search.domain.Item;
import com.search.domain.upstream.ITunesAlbumsResponse;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static java.text.MessageFormat.format;

@Service
@Slf4j
public class ITunesAlbumsService implements ItemsService {

    private final String url;
    private final int resultsLimit;
    private final int timeoutSecs;
    private final WebInvoker webInvoker;

    ITunesAlbumsService(WebInvoker webInvoker,
                        @Value("${upstream.iTunes.url}") String url,
                        @Value("${upstream.iTunes.results-limit}") int resultsLimit,
                        @Value("${upstream.iTunes.timeout-secs}") int timeoutSecs) {
        this.url = url;
        this.resultsLimit = resultsLimit;
        this.timeoutSecs = timeoutSecs;
        this.webInvoker = webInvoker;
    }

    @Override
    public Flux<Item> search(String searchValue) {
        log.info("calling iTunes api");
        return Mono.just(searchValue)
                .flatMap(this::callUpstream)
                .flatMap(clientResponse -> clientResponse.bodyToMono(ITunesAlbumsResponse.class))
                .map(ITunesAlbumsResponse::getResults)
                .flatMapMany(Flux::fromIterable)
                .filter(album -> album.getTrackName() != null)
                .take(resultsLimit)
                .map(album -> (Item)new Album(album.getTrackName(), Arrays.asList(album.getArtistName())))
                .onErrorResume(this::logErrorAndGetEmptyFlux);
    }

    private Mono<ClientResponse> callUpstream(String searchValue) {
        return webInvoker.GET(format(url, searchValue), timeoutSecs);
    }

    private Publisher<Item> logErrorAndGetEmptyFlux(Throwable throwable) {
        log.error("Error in providing ITunes Albums ", throwable);
        return Flux.empty();
    }
}
