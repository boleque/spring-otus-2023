package ru.otus.library.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class MongoItemCommentCascadeAddEventsListener extends AbstractMongoEventListener<Comment> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Comment> event) {
        super.onAfterSave(event);
        Comment comment = event.getSource();
        Book book = comment.getBook();
        List<Comment> bookComments = book.getComments();
        if (Objects.nonNull(bookComments)) {
            book.getComments().add(comment);
        } else {
            List<Comment> comments = new ArrayList<>();
            comments.add(comment);
            book.setComments(comments);
        }
        bookRepository.save(book);
    }
}
