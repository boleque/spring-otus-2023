package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.utils.DtoConverters;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors/all")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository
                .findAll()
                .map(DtoConverters::convertToAuthorDto);
    }
}
