package ru.otus.library.converters;

import org.springframework.stereotype.Service;
import ru.otus.library.models.Comment;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentConverter {

    public String convertCommentToString(Comment comment) {
        return String.format(
                "id: %s;  book title: %s; text: %s",
                comment.getId(),
                comment.getBook().getTitle(),
                comment.getText()
        );
    }

    public String convertCommentsToString(List<Comment> comments) {
        return comments.stream()
                .map(this::convertCommentToString)
                .collect(Collectors.joining("\n"));
    }
}
