package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;

import java.util.List;


@Service
public interface BookService {

    void createBook(String title, long authorId, long genreId);

    void updateBookTitle(long bookId, String title);

    void deleteById(long bookId);

    Book getById(long id);

    List<Book> getAll();

    List<Comment> getAllCommentsByBookId(long id);
}
