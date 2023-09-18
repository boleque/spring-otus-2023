package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;


@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(long id) throws EntityNotFoundException {
        Optional<Genre> optionalAuthor = genreRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        }
        throw new EntityNotFoundException("Genre s not found");
    }
}
