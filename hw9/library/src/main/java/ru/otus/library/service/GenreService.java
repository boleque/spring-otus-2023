package ru.otus.library.service;

import ru.otus.library.dto.GenreDto;
import ru.otus.library.models.Genre;

import java.util.List;


public interface GenreService {

    List<GenreDto> getAll();

    Genre getById(long id);

}
