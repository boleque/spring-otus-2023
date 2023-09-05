package ru.otus.library.converters;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreConverter {

    public String convertGenreToString(Genre genre) {
        return String.format(
                "id: %s; name: %s",
                genre.getId(),
                genre.getName()
        );
    }

    public String convertGenresToString(List<Genre> genres) {
        return genres.stream()
                .map(this::convertGenreToString)
                .collect(Collectors.joining("\n"));
    }
}
