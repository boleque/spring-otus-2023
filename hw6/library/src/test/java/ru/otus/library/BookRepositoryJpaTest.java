package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepositoryJpa;
import ru.otus.library.repositories.CommentRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import({BookRepositoryJpa.class, CommentRepositoryJpa.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static long FIRST_BOOK_ID = 1L;

    private static String FIRST_BOOK_TITLE = "The Desert of Ice";

    private static String FIRST_AUTHOR_NAME = "Jules Verne";

    private static String FIRST_GENRE_NAME = "adventure";

    private static final int FIRST_BOOK_NUMBER_OF_COMMENTS = 2;

    private static String NEW_BOOK_TITLE = "Journey to the Centre of the Earth";

    private static String CHANGED_FIRST_BOOK_TITLE = "An Antarctic Mystery";

    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 0;

    @Test
    void shouldCreateNewBook() {
        assertThat(bookRepositoryJpa.getByTitle(NEW_BOOK_TITLE)).isEmpty();

        Author author = em.find(Author.class, 1);
        Genre genre = em.find(Genre.class, 1);
        Book savedBook = bookRepositoryJpa.save(
                new Book(0, NEW_BOOK_TITLE, author, genre)
        );

        Book book = em.find(Book.class, savedBook.getId());
        assertNotNull(book);
        assertThat(book)
                .matches(s -> s.getAuthor() != null)
                .matches(s -> s.getGenre() != null)
                .isSameAs(savedBook);
    }

    @Test
    void shouldUpdateBook() {
        Book bookToChange = em.find(Book.class, FIRST_BOOK_ID);
        bookToChange.setTitle(CHANGED_FIRST_BOOK_TITLE);
        bookRepositoryJpa.save(bookToChange);

        Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertNotNull(book);
        assertThat(book)
                .matches(b -> b.getId() == FIRST_BOOK_ID)
                .matches(b -> b.getTitle().equals(CHANGED_FIRST_BOOK_TITLE))
                .matches(b -> b.getAuthor().getName().equals(FIRST_AUTHOR_NAME))
                .matches(b -> b.getGenre().getName().equals(FIRST_GENRE_NAME));
    }

    @Test
    void shouldReturnBookById() {
        Optional<Book> optionalBook = bookRepositoryJpa.getById(FIRST_BOOK_ID);
        assertTrue(optionalBook.isPresent());
        Book book = optionalBook.get();
        assertThat(book)
                .matches(b -> b.getId() == FIRST_BOOK_ID)
                .matches(b -> b.getTitle().equals(FIRST_BOOK_TITLE))
                .matches(b -> b.getAuthor().getName().equals(FIRST_AUTHOR_NAME))
                .matches(b -> b.getGenre().getName().equals(FIRST_GENRE_NAME));
    }

    @Test
    void shouldReturnBookByTitle() {
        Optional<Book> optionalBook = bookRepositoryJpa.getByTitle(FIRST_BOOK_TITLE);
        assertTrue(optionalBook.isPresent());
        Book book = optionalBook.get();
        assertThat(book)
                .matches(b -> b.getId() == FIRST_BOOK_ID)
                .matches(b -> b.getTitle().equals(FIRST_BOOK_TITLE))
                .matches(b -> b.getAuthor().getName().equals(FIRST_AUTHOR_NAME))
                .matches(b -> b.getGenre().getName().equals(FIRST_GENRE_NAME));
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> books = bookRepositoryJpa.getAll();
        assertThat(books)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_BOOKS);
    }

    @Test
    void shouldDeleteBookById() {
        Book bookToDelete = em.find(Book.class, FIRST_BOOK_ID);

        assertNotNull(bookToDelete);

        List<Comment> commentsToDelete = commentRepositoryJpa.getAllCommentsByBookId(FIRST_BOOK_ID);
        assertThat(commentsToDelete)
                .isNotNull()
                .hasSize(FIRST_BOOK_NUMBER_OF_COMMENTS);

        bookRepositoryJpa.delete(bookToDelete);

        Optional<Book> optionalBook = bookRepositoryJpa.getById(FIRST_BOOK_ID);
        assertFalse(optionalBook.isPresent());
        List<Comment> comments = commentRepositoryJpa.getAllCommentsByBookId(FIRST_BOOK_ID);
        assertThat(comments)
                .hasSize(EXPECTED_NUMBER_OF_COMMENTS);
    }
}