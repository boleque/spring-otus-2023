package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.models.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long>  {
}
