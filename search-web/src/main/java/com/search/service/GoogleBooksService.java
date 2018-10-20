package com.search.service;

import com.search.domain.Book;
import com.search.domain.Item;
import com.search.domain.upstream.GoogleBooksResponse;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.text.MessageFormat.format;

@Service
@Slf4j
public class GoogleBooksService implements ItemsService {

    private final String url;
    private final int resultsLimit;
    private final int timeoutSecs;
    private final WebInvoker webInvoker;

    public GoogleBooksService(WebInvoker webInvoker,
                              @Value("${upstream.google-books.url}") String url,
                              @Value("${upstream.google-books.results-limit}") int resultsLimit,
                              @Value("${upstream.google-books.timeout-secs}") int timeoutSecs) {
        this.url = url;
        this.resultsLimit = resultsLimit;
        this.timeoutSecs = timeoutSecs;
        this.webInvoker = webInvoker;
    }

    @Override
    public Flux<Item> search(final String searchValue) {
        log.info("calling google books api");
        return Mono.just(searchValue)
                .flatMap(this::callUpstream)
                .flatMap(clientResponse -> clientResponse.bodyToMono(GoogleBooksResponse.class))
                .map(GoogleBooksResponse::getItems)
                .flatMapMany(Flux::fromIterable)
                .filter(book -> book.getVolumeInfo().getTitle() != null)
                .take(resultsLimit)
                .map(book -> (Item)new Book(book.getVolumeInfo().getTitle(), book.getVolumeInfo().getAuthors()))
                .onErrorResume(this::logErrorAndGetEmptyFlux);
    }

    private Mono<ClientResponse> callUpstream(String searchValue) {
        return webInvoker.GET(format(url, searchValue), timeoutSecs);
    }

    private Publisher<Item> logErrorAndGetEmptyFlux(Throwable throwable) {
        log.error("Error in fetching Google Books ", throwable);
        return Flux.empty();
    }
}
