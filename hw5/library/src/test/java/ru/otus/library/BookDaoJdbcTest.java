package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.dao.BookDaoJdbc;
import ru.otus.library.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

    private static final long EXISTING_BOOK_ID = 1;

    private static final String EXISTING_BOOK_TITLE = "The Desert of Ice";

    private static final String EXISTING_BOOK_AUTHOR = "Jules Verne";

    private static final String EXISTING_BOOK_GENRE = "adventure";

    @Autowired
    private BookDaoJdbc bookDao;

    @Test
    void shouldReturnExpectedBookByTitle() {
        var expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        var actualBook = bookDao.getByTitle(expectedBook.getTitle());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        var actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldReturnExpectedBooksList() {
        var expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        List<Book> actualBooksList = bookDao.getAll();
        assertThat(actualBooksList)
                .containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void shouldInsertBook() {
        var expectedBook = new Book(2, "A Journey to the Centre of the Earth", EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        bookDao.create(expectedBook.getTitle(), 1, 1);
        var actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        var newTitle = "A Journey to the Centre of the Earth";
        var expectedBook = new Book(EXISTING_BOOK_ID, newTitle, EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        bookDao.update(EXISTING_BOOK_ID, newTitle, 1, 1);
        var actualBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}
