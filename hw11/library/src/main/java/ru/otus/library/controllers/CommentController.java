package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.utils.DtoConverters;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping("/comments/all")
    public Flux<CommentDto> getAllComments() {
        return commentRepository
                .findAll()
                .map(DtoConverters::convertToCommentDto);
    }
}
