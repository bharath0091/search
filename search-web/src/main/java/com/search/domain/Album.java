package com.search.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@Getter
public final class Album extends Item {

    private final List<String> artists;

    public Album(String title, List<String> artists) {
        super(title, ItemType.ALBUM);
        this.artists = artists == null ? null : Collections.unmodifiableList(artists);
    }
}
