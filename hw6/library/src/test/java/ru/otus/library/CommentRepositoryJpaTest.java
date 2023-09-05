package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepositoryJpa;

import java.util.List;
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

    private static final long FIRST_BOOK_ID = 1L;

    private static final long BOOK_ID_WITHOUT_COMMENTS = 20L;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

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

    @Test
    void shouldReturnCorrectCommentsByBookId() {
        List<Comment> comments = repositoryJpa.getAllCommentsByBookId(FIRST_BOOK_ID);
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(c -> c.getBook() != null);
    }

    @Test
    void shouldReturnEmptyCommentsByBookId() {
        List<Comment> comments = repositoryJpa.getAllCommentsByBookId(BOOK_ID_WITHOUT_COMMENTS);
        assertThat(comments).isNotNull().hasSize(0);
    }
}