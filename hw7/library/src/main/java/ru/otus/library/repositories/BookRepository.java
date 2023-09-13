package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Book;


@Component
public interface BookRepository extends JpaRepository<Book, Long> {
}
