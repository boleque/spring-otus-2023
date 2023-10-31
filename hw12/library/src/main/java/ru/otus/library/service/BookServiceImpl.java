package ru.otus.library.service;

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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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

    @Override
    public List<ReadBookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(DtoConverters::convertToReadBookDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByBookId(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> book.getComments().stream().toList())
                .orElse(
                        Collections.emptyList()
                );
    }

    @Transactional
    @Override
    public void deleteById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public ReadBookDto getById(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException.withIdAndClass(bookId, Book.class));
        return DtoConverters.convertToReadBookDto(book);
    }
}
