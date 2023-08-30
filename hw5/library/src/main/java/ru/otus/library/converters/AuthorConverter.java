package ru.otus.library.converters;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuthorConverter {

    public String convertAuthorToString(Author author) {
        return String.format(
                "id: %s; name: %s",
                author.getId(),
                author.getName()
        );
    }

    public String convertAuthorsToString(List<Author> genres) {
        return genres.stream()
                .map(this::convertAuthorToString)
                .collect(Collectors.joining("\n"));
    }
}
