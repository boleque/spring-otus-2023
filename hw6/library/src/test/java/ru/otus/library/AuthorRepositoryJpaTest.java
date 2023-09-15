package ru.otus.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Author;
import ru.otus.library.repositories.AuthorRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 1;

    private static final int FIRST_AUTHOR_ID = 1;

    private static String FIRST_AUTHOR_NAME = "Jules Verne";


    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> authors = repositoryJpa.getAll();
        Assertions.assertEquals(EXPECTED_NUMBER_OF_AUTHORS, authors.size());
    }

    @Test
    void shouldReturnCorrectAuthorById() {
        Optional<Author> optionalAuthor = repositoryJpa.getById(1L);
        assertTrue(optionalAuthor.isPresent());
        Author author = optionalAuthor.get();
        assertThat(author)
                .matches(b -> b.getId() == FIRST_AUTHOR_ID)
                .matches(b -> b.getName().equals(FIRST_AUTHOR_NAME));
    }

    @DisplayName("Requesting an author by a non-existent id")
    @Test
    void shouldReturnNonPresentAuthorOptional() {
        Optional<Author> optionalAuthor = repositoryJpa.getById(2L);
        assertFalse(optionalAuthor.isPresent());
    }
}