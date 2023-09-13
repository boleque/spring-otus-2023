package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Comment;


@Component
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
