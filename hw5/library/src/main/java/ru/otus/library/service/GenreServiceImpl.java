package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
