package ru.otus.library.service;

import ru.otus.library.dto.ReadCommentDto;

import java.util.List;


public interface CommentService {

    ReadCommentDto getCommentById(long id);

    List<ReadCommentDto> getAll();
}
