package com.search.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@Getter
public final class Book extends Item {

    private final List<String> authors;

    public Book(String title, List<String> authors) {
        super(title, ItemType.BOOK);
        this.authors = authors == null ? null : Collections.unmodifiableList(authors);
    }
}
