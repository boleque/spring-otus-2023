package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.AuthorConverter;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;

import java.util.List;

@Component
public class GetAllAuthorsCommandProcessor implements CommandProcessor {

    private final AuthorService authorService;

    private final AuthorConverter converter;

    private final IOService ioService;

    private final ShellOption getAllAuthorsMenuOption;

    public GetAllAuthorsCommandProcessor(
            AuthorService authorService,
            IOService ioService,
            AuthorConverter converter,
            ShellOption getAllAuthorsMenuOption) {
        this.authorService = authorService;
        this.ioService = ioService;
        this.converter = converter;
        this.getAllAuthorsMenuOption = getAllAuthorsMenuOption;
    }

    @Override
    public void processCommand() {
        List<Author> genreList = authorService.getAll();
        String result = converter.convertAuthorsToString(genreList);
        ioService.outputString(result);
    }

    @Override
    public ShellOption getProcessedCommandOption() {
        return getAllAuthorsMenuOption;
    }
}
