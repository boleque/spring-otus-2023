package ru.otus.library.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.BookRepository;


@Component
@RequiredArgsConstructor
public class MongoItemCommentCascadeDeleteEventsListener extends AbstractMongoEventListener<Comment> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Comment> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val commentId = source.get("_id").toString();
        bookRepository.removeCommentArrayElementsById(commentId);
    }
}
