package ru.otus.library.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MongoAllCommentsCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val bookId = source.get("_id").toString();
        List<Comment> comments = commentRepository.findAllByBookId(bookId);
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }
    }
}
