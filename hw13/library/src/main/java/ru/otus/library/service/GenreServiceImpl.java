package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.service.utils.DtoConverters;

import java.util.List;


@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> getAll() {
        return genreRepository.findAll().stream()
                .map(DtoConverters::convertToGenreDto)
                .toList();
    }

    @Override
    public Genre getById(long id) throws EntityNotFoundException {
        return genreRepository.findById(id).orElseThrow(EntityNotFoundException.withIdAndClass(id, Genre.class));
    }
}
