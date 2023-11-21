package ru.otus.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.events.MongoItemCommentCascadeAddEventsListener;
import ru.otus.library.events.MongoAllCommentsCascadeDeleteEventsListener;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Import({
        MongoAllCommentsCascadeDeleteEventsListener.class,
        MongoItemCommentCascadeAddEventsListener.class
})
@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String TO_DELETE_BOOK_ID = "650b24368e41eb0c203f2674";

    private final static int TO_DELETE_BOOK_COMMENTS_NUMBER = 2;

    private final static String TO_DELETE_BOOK_COMMENT_ID = "650b24368e41eb0c203f2677";

    private final static String TO_CREATE_BOOK_ID = "650b24368e41eb0c203f2676";

    @Test
    public void shouldCorrectlyDeleteBookById() {
        // check book exists
        Optional<Book> bookToDeleteOptional = bookRepository.findById(TO_DELETE_BOOK_ID);
        Assertions.assertTrue(bookToDeleteOptional.isPresent());
        // check books comments exist
        Assertions.assertEquals(TO_DELETE_BOOK_COMMENTS_NUMBER, bookToDeleteOptional.get().getComments().size());

        bookRepository.deleteById(TO_DELETE_BOOK_ID);

        // check comments are deleted
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(TO_DELETE_BOOK_COMMENT_ID));
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        Assertions.assertEquals(0, comments.size());
    }

    @Test
    public void shouldCorrectlySaveBook() {
        Optional<Book> nonExistingBookOptional = bookRepository.findById(TO_CREATE_BOOK_ID);
        Assertions.assertFalse(nonExistingBookOptional.isPresent());
        Book newBook = new Book(
                TO_CREATE_BOOK_ID,
                "From the Earth to the Moon",
                new Author("1", "Jules Verne"),
                new Genre("1", "adventure"),
                null
        );
        bookRepository.save(newBook);
         // check book is created
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(TO_CREATE_BOOK_ID));
        List<Book> books = mongoTemplate.find(query, Book.class);
        Assertions.assertEquals(1, books.size());
        Book createdBook = books.get(0);
        Assertions.assertEquals(TO_CREATE_BOOK_ID, createdBook.getId());
    }
}
