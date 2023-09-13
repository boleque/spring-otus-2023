package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Genre;


@Component
public interface GenreRepository extends JpaRepository<Genre, Long>  {
}
