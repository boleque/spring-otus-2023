package ru.otus.library.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Book;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;


@Component
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> optionalBook = getById(id);
        optionalBook.ifPresent(this::delete);
    }
}
