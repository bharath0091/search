package com.search.domain;

import lombok.Getter;


@Getter
public abstract class Item {
    private final String title;
    private final ItemType type;

    public Item(String title, ItemType type) {
        this.title = title;
        this.type = type;
    }
}
