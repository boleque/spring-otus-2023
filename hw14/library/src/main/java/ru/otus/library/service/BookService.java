package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;

import java.util.List;


@Service
public interface BookService {

    void createBook(String title, String authorId, String genreId);

    void updateBookTitle(String bookId, String title);

    void deleteById(String bookId);

    Book getById(String id);

    List<Book> getAll();

    List<Comment> getAllCommentsByBookId(String id);
}
