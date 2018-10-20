package com.search.handler;

import com.search.domain.Item;
import com.search.service.ItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@Slf4j
public class SearchHandler {

    private final List<ItemsService> itemsServices;
    private static final String SEARCH_QUERY_PARAM_NAME = "q";
    private static final Mono<String> INVALID_SEARCH = Mono.just("Invalid Search");

    public SearchHandler(List<ItemsService> itemsServices) {
        this.itemsServices = Collections.unmodifiableList(itemsServices);
    }

    public Mono<ServerResponse> search(ServerRequest request) {
        return request.queryParam(SEARCH_QUERY_PARAM_NAME)
                      .map(this::search)
                      .orElse(badRequest());
    }

    private Mono<ServerResponse> search(String searchValue) {
        Flux<Item> items = Flux.fromIterable(itemsServices)
                               .flatMap(provider -> provider.search(searchValue))
                               .sort(Comparator.comparing(Item::getTitle));
        return ServerResponse.ok().body(items, Item.class);
    }

    private Mono<ServerResponse> badRequest() {
        return ServerResponse.badRequest().body(INVALID_SEARCH, String.class);
    }
}