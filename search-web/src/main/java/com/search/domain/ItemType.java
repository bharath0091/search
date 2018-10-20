package com.search.domain;

public enum ItemType {
    BOOK("Book"),
    ALBUM("Album");

    private final String displayName;

    ItemType(final String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
