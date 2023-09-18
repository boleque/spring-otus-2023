package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 1;

    private static final String BOOK_ID = "1";

    private static final String BOOK_AUTHOR = "Mark Twain";

    private static final String BOOK_GENRE = "Novels";

    private static final String BOOK_TITLE = "Tom Soyer";

    @Test
    void shouldReturnAllCommentsByBookId() {
        Book book = new Book(BOOK_ID, BOOK_TITLE, new Author("1", BOOK_AUTHOR), new Genre("1", BOOK_GENRE), null);
        Comment comment = new Comment("1", "Interesting Book", book);
        book.setComments(List.of(comment));
        given(bookRepository.findById(anyString())).willReturn(
                Optional.of(book)
        );
        List<Comment> comments = bookService.getAllCommentsByBookId(BOOK_ID);
        assertThat(comments)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_COMMENTS);
    }

    @Test
    public void requestNonExistingBookByBookId() {
        given(bookRepository.findById(anyString())).willReturn(
                Optional.empty()
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(anyString())
        );
    }

    @Test
    public void createBookWithNonExistingGenre() {
        // author exists
        given(authorService.getById(anyString())).willReturn(
                new Author("1", BOOK_AUTHOR)
        );
        // genre doesn't exist
        given(genreService.getById(anyString())).willThrow(
                new EntityNotFoundException("Genre is not found")
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.createBook(BOOK_TITLE, "1", anyString())
        );
    }

    @Test
    public void updateBookWithNonExistingAuthor() {
        // author doesn't exist
        given(authorService.getById(anyString())).willThrow(
                new EntityNotFoundException("Author is not found")
        );
        // genre exists
        given(genreService.getById(anyString())).willReturn(
                new Genre("1", BOOK_GENRE)
        );
        assertThrows(EntityNotFoundException.class,
                () -> bookService.createBook(BOOK_TITLE, "1", anyString())
        );
    }
}
