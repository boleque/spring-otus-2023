package ru.otus.library.dao;

import ru.otus.library.domain.Book;


import java.util.List;

public interface BookDao {

    void create(String title, long authorId, long genreId);

    void update(long id, String title, long authorId, long genreId);

    void deleteById(long id);

    Book getById(long id);

    Book getByTitle(String title);

    List<Book> getAll();
}
