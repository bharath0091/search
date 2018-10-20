package com.search.domain.upstream;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ITunesAlbumsResponse {
    private List<Album> results;

    @Getter
    @Setter
    public static class Album {
        private String artistName;
        private String trackName;
    }
}
