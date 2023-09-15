package ru.otus.library.repositories;

import ru.otus.library.models.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository {
    List<Author> getAll();

    Optional<Author> getById(long id);
}
