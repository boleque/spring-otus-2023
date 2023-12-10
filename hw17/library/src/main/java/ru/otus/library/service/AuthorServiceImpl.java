package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Author;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.service.utils.DtoConverters;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream()
                .map(DtoConverters::convertToAuthorDto)
                .toList();
    }

    @Override
    public Author getById(long id) throws EntityNotFoundException {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException.withIdAndClass(id, Author.class));
    }
}
