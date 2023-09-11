package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getCommentById(long id) throws EntityNotFoundException {
        Optional<Comment> optionalComment = commentRepository.getCommentById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        }
        throw new EntityNotFoundException("Comment is not found");
    }

    @Override
    public List<Comment> getAllCommentsByBookId(long bookId) {
        return commentRepository.getAllCommentsByBookId(bookId);
    }
}
