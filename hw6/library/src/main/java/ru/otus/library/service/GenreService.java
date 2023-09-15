package ru.otus.library.service;

import ru.otus.library.models.Genre;

import java.util.List;


public interface GenreService {

    List<Genre> getAll();

    Genre getById(long id);

}
