package ru.otus.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dto.SaveBookDto;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.service.utils.DtoConverters;
import ru.otus.library.dto.ReadBookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreService genreService;

    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, GenreService genreService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @Override
    public void saveBook(SaveBookDto book) {
        Long bookId = book.getId();
        Author author = authorService.getById(book.getAuthorId());
        Genre genre = genreService.getById(book.getGenreId());
        String title = book.getTitle();
        Book model;
        if (Objects.isNull(bookId)) {
            model = new Book();
        } else {
            model = bookRepository.findById(bookId)
                    .orElseThrow(EntityNotFoundException.withIdAndClass(bookId, Book.class));
        }
        model.setTitle(title);
        model.setAuthor(author);
        model.setGenre(genre);

        bookRepository.save(model);
    }

    @HystrixCommand(commandKey = "getAllBooks", fallbackMethod = "getBooksListUndefined")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public List<ReadBookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(DtoConverters::convertToReadBookDto)
                .collect(Collectors.toList());
    }

    @HystrixCommand(commandKey = "getAllCommentsByBookId", fallbackMethod = "getAllCommentsByBookUndefined")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByBookId(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> new ArrayList<>(book.getComments()))
                .orElseThrow(
                        EntityNotFoundException.withIdAndClass(id, Comment.class)
                );
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @HystrixCommand(commandKey = "getBookById", fallbackMethod = "getBookUndefined")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ReadBookDto getById(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException.withIdAndClass(bookId, Book.class));
        return DtoConverters.convertToReadBookDto(book);
    }

    private List<ReadBookDto> getBooksListUndefined() {
        return List.of(
                new ReadBookDto(0L, "n/a", "n/a", "n/a")
        );
    }

    private ReadBookDto getBookUndefined() {
        return new ReadBookDto(0L, "n/a", "n/a", "n/a");
    }

    private List<Comment> getAllCommentsByBookUndefined() {
        return List.of(
                new Comment(0L, "n/a", null)
        );
    }
}
