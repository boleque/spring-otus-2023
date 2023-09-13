package ru.otus.library.converters;

import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookConverter {

    public String convertBookToString(Book book) {
        return String.format(
                "id: %s; title: %s; author: %s; genre: %s",
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre()
        );
    }

    public String convertBookToString(List<Book> books) {
        return books.stream()
                .map(this::convertBookToString)
                .collect(Collectors.joining("\n"));
    }
}
