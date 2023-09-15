package ru.otus.library.repositories;

import ru.otus.library.models.Genre;

import java.util.List;
import java.util.Optional;


public interface GenreRepository {
    List<Genre> getAll();

    Optional<Genre> getById(long id);
}
