package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.service.utils.DtoConverters;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;

import java.util.List;
import java.util.Objects;


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
    public BookDto saveBook(BookDto bookDto) {
        Long bookId = bookDto.getId();
        AuthorDto authorDto = bookDto.getAuthor();
        GenreDto genreDto = bookDto.getGenre();
        Author authorModel = authorService.getById(authorDto.getId());
        Genre genreModel = genreService.getById(genreDto.getId());
        String title = bookDto.getTitle();
        Book model;
        if (Objects.isNull(bookId)) {
            model = new Book();
        } else {
            model = bookRepository.findById(bookId)
                    .orElseThrow(EntityNotFoundException.withIdAndClass(bookId, Book.class));
        }
        model.setTitle(title);
        model.setAuthor(authorModel);
        model.setGenre(genreModel);

        return DtoConverters.convertToBookDto(bookRepository.save(model));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(DtoConverters::convertToBookDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookCommentDto getAllCommentsByBookId(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException.withIdAndClass(bookId, Book.class));
        return DtoConverters.convertToBookCommentDto(book);
    }

    @Transactional
    @Override
    public void deleteById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public BookDto getById(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException.withIdAndClass(bookId, Book.class));
        return DtoConverters.convertToBookDto(book);
    }
}
