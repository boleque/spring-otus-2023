package ru.otus.library.repositories;

import ru.otus.library.models.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    Book save(Book book);

    void delete(Book book);

    void deleteById(long id);

    Optional<Book> getById(long id) ;

    Optional<Book> getByTitle(String title);

    List<Book> getAll();
}
