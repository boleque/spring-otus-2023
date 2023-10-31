package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dto.ReadCommentDto;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.service.utils.DtoConverters;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public ReadCommentDto getCommentById(long id) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException.withIdAndClass(id, Comment.class));
        return DtoConverters.convertToReadCommentDto(comment);
    }

    @Override
    public List<ReadCommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(DtoConverters::convertToReadCommentDto)
                .toList();
    }
}
