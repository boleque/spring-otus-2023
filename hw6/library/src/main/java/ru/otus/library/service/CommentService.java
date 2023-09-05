package ru.otus.library.service;

import ru.otus.library.models.Comment;

import java.util.List;


public interface CommentService {

    Comment getCommentById(long id);

    List<Comment> getAllCommentsByBookId(long bookId);

}
