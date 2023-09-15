package ru.otus.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.GenreRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 1;

    private static final long FIRST_GENRE_ID = 1L;

    private static final String FIRST_GENRE_NAME = "adventure";

    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Genre> genres = repositoryJpa.getAll();
        Assertions.assertEquals(EXPECTED_NUMBER_OF_GENRES, genres.size());
    }

    @Test
    void shouldReturnCorrectGenreById() {
        Optional<Genre> optionalGenre = repositoryJpa.getById(1L);
        assertTrue(optionalGenre.isPresent());
        Genre genre = optionalGenre.get();
        assertThat(genre)
                .matches(g -> g.getId() == FIRST_GENRE_ID)
                .matches(g -> g.getName().equals(FIRST_GENRE_NAME));
    }

    @DisplayName("Requesting a genre by a non-existent id")
    @Test
    void shouldReturnNonPresentAuthorOptional() {
        Optional<Genre> optionalBook = repositoryJpa.getById(10L);
        assertFalse(optionalBook.isPresent());
    }
}