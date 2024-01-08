package ru.otus.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.library.dto.ReadCommentDto;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.service.utils.DtoConverters;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @HystrixCommand(commandKey = "getCommentById", fallbackMethod = "getCommentUndefined")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public ReadCommentDto getCommentById(long id) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException.withIdAndClass(id, Comment.class));
        return DtoConverters.convertToReadCommentDto(comment);
    }

    @HystrixCommand(commandKey = "getAllComments", fallbackMethod = "getCommentsListUndefined")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public List<ReadCommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(DtoConverters::convertToReadCommentDto)
                .collect(Collectors.toList());
    }

    private ReadCommentDto getCommentUndefined() {
        return new ReadCommentDto(0L, "n/a", "n/a");
    }

    private List<ReadCommentDto> getCommentsListUndefined() {
        return List.of(
                new ReadCommentDto(0L, "n/a", "n/a")
        );
    }
}
