package ru.otus.library.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {

    private final long id;

    private final String title;

    private final String author;

    private final String genre;
}
