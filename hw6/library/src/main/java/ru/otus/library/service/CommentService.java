package ru.otus.library.service;

import ru.otus.library.models.Comment;


public interface CommentService {
    Comment getCommentById(long id);
}
