package ru.otus.library;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Author;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.service.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@SpringBootTest(classes = {BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class} )
@ExtendWith({SpringExtension.class})
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    private static final String BOOK_AUTHOR = "Mark Twain";

    private static final String BOOK_TITLE = "Tom Soyer";

    @Test
    public void requestNonExistingBookByBookId() {
        given(bookRepository.getById(anyLong())).willReturn(
                Optional.empty()
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(anyLong())
        );
    }

    @Test
    public void requestNonExistingBookByBookTitle() {
        given(bookRepository.getByTitle(anyString())).willReturn(
                Optional.empty()
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.getByTitle(anyString())
        );
    }

    @Test
    public void createBookWithNonExistingGenre() {
        // author exists
        given(authorRepository.getById(anyLong())).willReturn(
                Optional.of(new Author(1L, BOOK_AUTHOR))
        );
        // genre doesn't exist
        given(genreRepository.getById(anyLong())).willReturn(
                Optional.empty()
        );

        assertThrows(EntityNotFoundException.class,
                () -> bookService.createBook(BOOK_TITLE, 1L, anyLong())
        );
    }

    @Test
    public void updateBookWithNonExistingAuthor() {
        // author doesn't exist
        given(authorRepository.getById(anyLong())).willReturn(
                Optional.of(new Author(1L, BOOK_AUTHOR))
        );
        // genre exists
        given(genreRepository.getById(anyLong())).willReturn(
                Optional.empty()
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.createBook(BOOK_TITLE, 1L, anyLong())
        );
    }
}
