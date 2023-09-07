package ru.otus.library.repositories;

import ru.otus.library.models.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentRepository {
    Optional<Comment> getCommentById(long id);

    List<Comment> getAllCommentsByBookId(long bookId);
}
