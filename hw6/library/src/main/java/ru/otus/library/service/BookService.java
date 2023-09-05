package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;

import java.util.List;


@Service
public interface BookService {

    public void createBook(String title, long authorId, long genreId);

    public void updateBookTitle(long bookId, String title);

    public void deleteById(long bookId);

    public Book getById(long id);

    public Book getByTitle(String title);

    public List<Book> getAll();

}
