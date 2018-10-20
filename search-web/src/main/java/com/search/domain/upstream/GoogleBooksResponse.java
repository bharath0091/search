package com.search.domain.upstream;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleBooksResponse {
    List<Book> items;

    @Getter
    @Setter
    public static class Book {
        private VolumeInfo volumeInfo;
    }

    @Getter
    @Setter
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
    }
}
