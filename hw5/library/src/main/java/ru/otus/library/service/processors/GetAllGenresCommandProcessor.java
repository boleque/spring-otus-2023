package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.GenreConverter;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;

import java.util.List;

@Component
public class GetAllGenresCommandProcessor implements CommandProcessor {

    private final GenreService genreService;

    private final GenreConverter converter;

    private final IOService ioService;

    private final ShellOption getAllGenresMenuOption;

    public GetAllGenresCommandProcessor(
            GenreService genreService,
            IOService ioService,
            GenreConverter converter,
            ShellOption getAllGenresMenuOption) {
        this.genreService = genreService;
        this.ioService = ioService;
        this.converter = converter;
        this.getAllGenresMenuOption = getAllGenresMenuOption;
    }

    @Override
    public void processCommand() {
        List<Genre> genreList = genreService.getAll();
        String result = converter.convertGenresToString(genreList);
        ioService.outputString(result);
    }

    @Override
    public ShellOption getProcessedCommandOption() {
        return getAllGenresMenuOption;
    }
}
