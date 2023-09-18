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
import ru.otus.library.events.MongoItemCommentCascadeDeleteEventsListener;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Import({
        MongoItemCommentCascadeAddEventsListener.class,
        MongoItemCommentCascadeDeleteEventsListener.class
})
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String TO_DELETE_COMMENT_ID = "650b24368e41eb0c203f2677";

    private final static int BOOK_COMMENTS_BEFORE_NUMBER = 2;

    private final static int BOOK_COMMENTS_AFTER_NUMBER = 1;

    private final static String EXISTING_BOOK_ID = "650b24368e41eb0c203f2674";

    @Test
    void shouldDeleteCommentFromBook() {
        Optional<Comment> commentToDeleteOptional = commentRepository.findById(TO_DELETE_COMMENT_ID);
        Assertions.assertTrue(commentToDeleteOptional.isPresent());
        // check books comments exist
        Book book = commentToDeleteOptional.get().getBook();
        Assertions.assertEquals(BOOK_COMMENTS_BEFORE_NUMBER, book.getComments().size());

        commentRepository.deleteById(TO_DELETE_COMMENT_ID);

        // check item comment is deleted from the book
        Query bookdByIdQuery = new Query();
        bookdByIdQuery.addCriteria(Criteria.where("_id").is(EXISTING_BOOK_ID));
        List<Book> books = mongoTemplate.find(bookdByIdQuery, Book.class);
        Assertions.assertEquals(BOOK_COMMENTS_AFTER_NUMBER, books.get(0).getComments().size());
    }
}
