package ru.otus.library;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.service.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@SpringBootTest(classes = {BookServiceImpl.class} )
@ExtendWith({SpringExtension.class})
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    private static final long BOOK_ID = 1L;

    private static final String BOOK_AUTHOR = "Mark Twain";

    private static final String BOOK_GENRE = "Novels";

    private static final String BOOK_TITLE = "Tom Soyer";

    @Test
    void shouldReturnAllCommentsByBookId() {
        given(bookRepository.getById(anyLong())).willReturn(
                Optional.of(
                    new Book(
                            BOOK_ID,
                            BOOK_TITLE,
                            new Author(1, BOOK_AUTHOR),
                            new Genre(1, BOOK_GENRE),
                            List.of(
                                    new Comment(1, "Interesting Book", BOOK_ID),
                                    new Comment(2, "Boring Book", BOOK_ID)
                            )
                    )
                )
        );
        List<Comment> comments = bookService.getAllCommentsByBookId(BOOK_ID);
        assertThat(comments)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_COMMENTS);
    }

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
    public void createBookWithNonExistingGenre() {
        // author exists
        given(authorService.getById(anyLong())).willReturn(
                new Author(1, BOOK_AUTHOR)
        );
        // genre doesn't exist
        given(genreService.getById(anyLong())).willThrow(
                new EntityNotFoundException("Genre is not found")
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.createBook(BOOK_TITLE, 1L, anyLong())
        );
    }

    @Test
    public void updateBookWithNonExistingAuthor() {
        // author doesn't exist
        given(authorService.getById(anyLong())).willThrow(
                new EntityNotFoundException("Author is not found")

        );
        // genre exists
        given(genreService.getById(anyLong())).willReturn(
                new Genre(1, BOOK_GENRE)
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.createBook(BOOK_TITLE, 1L, anyLong())
        );
    }
}
