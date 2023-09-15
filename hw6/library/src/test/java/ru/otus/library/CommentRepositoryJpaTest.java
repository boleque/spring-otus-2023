package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepositoryJpa;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final long NON_EXISTING_COMMENT_ID = 20L;

    @Test
    void shouldReturnCorrectCommentById() {
        Optional<Comment> optionalActualComment = repositoryJpa.getCommentById(FIRST_COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get().usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void shouldReturnNonPresentCommentOptional() {
        Optional<Comment> optionalComment = repositoryJpa.getCommentById(NON_EXISTING_COMMENT_ID);
        assertFalse(optionalComment.isPresent());
    }
}