package ru.otus.library.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Genre {

    private final long id;

    private final String name;
}
