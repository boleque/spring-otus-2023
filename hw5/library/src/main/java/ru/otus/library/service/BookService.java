package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import java.util.List;


@Service
public interface BookService {

    public void create(String title, long authorId, long genreId);

    public void update(long id, String title, long authorId, long genreId);

    public void deleteById(long id);

    public Book getById(long id);

    public Book getByTitle(String title);

    public List<Book> getAll();

}
