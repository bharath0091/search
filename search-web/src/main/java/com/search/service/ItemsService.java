package com.search.service;

import com.search.domain.Item;
import reactor.core.publisher.Flux;

public interface ItemsService {
    Flux<Item> search(String searchValue);
}
