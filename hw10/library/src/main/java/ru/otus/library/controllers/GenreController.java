package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.service.GenreService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres/all")
    public List<GenreDto> getAllGenres() {
        return genreService.getAll();
    }
}
