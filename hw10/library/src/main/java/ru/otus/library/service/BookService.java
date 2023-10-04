package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.dto.BookDto;

import java.util.List;


@Service
public interface BookService {

    BookDto saveBook(BookDto book);
    
    void deleteById(long bookId);

    BookDto getById(long bookId);

    List<BookDto> getAll();

    BookCommentDto getAllCommentsByBookId(long id);
}
