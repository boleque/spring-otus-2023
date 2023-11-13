package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Author;
import ru.otus.library.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(String id) throws EntityNotFoundException {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        }
        throw new EntityNotFoundException("Author is not found");
    }
}
