package ru.otus.library.service;

import ru.otus.library.models.Author;

import java.util.List;


public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}
