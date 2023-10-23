package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.utils.DtoConverters;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/books/all")
    public Flux<BookDto> getAllBooks() {
        return bookRepository
                .findAll()
                .map(DtoConverters::convertToBookDto);
    }

    @GetMapping("/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable("id") String id) {
        return bookRepository
                .findById(id)
                .map(DtoConverters::convertToBookDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));

    }

    @GetMapping("/books/{id}/comments")
    public Mono<ResponseEntity<BookCommentDto>> getBookComments(@PathVariable("id") String id) {
        return bookRepository
                .findById(id)
                .map(DtoConverters::convertToBookCommentDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));

    }

    @PutMapping("/books")
    public Mono<BookDto> editBook(@RequestBody BookDto bookDto) {
        final AuthorDto authorDto = bookDto.getAuthor();
        final GenreDto genreDto = bookDto.getGenre();
        return bookRepository.findById(bookDto.getId()).flatMap(
                book -> {
                    book.setTitle(bookDto.getTitle());
                    book.setAuthor(new Author(authorDto.getId(), authorDto.getName()));
                    book.setGenre(new Genre(genreDto.getId(), genreDto.getName()));

                    return bookRepository.save(book);
                }
        ).map(DtoConverters::convertToBookDto);
    }

    @PostMapping("/books")
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {
        final AuthorDto authorDto = bookDto.getAuthor();
        final GenreDto genreDto = bookDto.getGenre();
        return bookRepository
                .save(
                        new Book(
                                bookDto.getTitle(),
                                new Author(authorDto.getId(), authorDto.getName()),
                                new Genre(genreDto.getId(), genreDto.getName())
                        )
                )
                .map(DtoConverters::convertToBookDto);
    }

    @DeleteMapping("/books/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }
}
