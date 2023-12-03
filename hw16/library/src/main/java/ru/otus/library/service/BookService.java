package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dto.ReadBookDto;
import ru.otus.library.dto.SaveBookDto;
import ru.otus.library.models.Comment;

import java.util.List;


@Service
public interface BookService {

    void saveBook(SaveBookDto book);

    void deleteById(long bookId);

    ReadBookDto getById(long bookId);

    List<ReadBookDto> getAll();

    List<Comment> getAllCommentsByBookId(long id);
}
