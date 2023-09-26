package ru.otus.library.service;

import ru.otus.library.dto.AuthorDto;
import ru.otus.library.models.Author;

import java.util.List;


public interface AuthorService {
    List<AuthorDto> getAll();

    Author getById(long id);
}
