package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.utils.DtoConverters;


@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/genres/all")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository
                .findAll()
                .map(DtoConverters::convertToGenreDto);
    }
}
