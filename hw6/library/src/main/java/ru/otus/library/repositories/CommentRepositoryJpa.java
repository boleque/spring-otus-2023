package ru.otus.library.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.library.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;


@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAllCommentsByBookId(long bookId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-books-entity-graph");
        TypedQuery<Comment> query = em.createQuery("select s " +
                        "from Comment s " +
                        "where s.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }
}
