package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.service.AuthorService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors/all")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll();
    }
}
