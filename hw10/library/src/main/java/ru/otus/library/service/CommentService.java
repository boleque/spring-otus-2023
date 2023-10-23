package ru.otus.library.service;

import ru.otus.library.dto.CommentDto;

import java.util.List;


public interface CommentService {

    CommentDto getCommentById(long id);

    List<CommentDto> getAll();
}
