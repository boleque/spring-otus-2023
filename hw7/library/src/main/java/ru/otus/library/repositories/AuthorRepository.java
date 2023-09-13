package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Author;

@Component
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
