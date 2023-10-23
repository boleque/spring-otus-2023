package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books/all")
    public List<BookDto> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        return bookService.getById(id);
    }

    @GetMapping("/books/{id}/comments")
    public BookCommentDto getBookComments(@PathVariable("id") long id) {
        return bookService.getAllCommentsByBookId(id);
    }

    @PutMapping("/books")
    public BookDto editBook(@RequestBody BookDto bookDto) {
        return bookService.saveBook(bookDto);
    }

    @PostMapping("/books")
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.saveBook(bookDto);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
