package ru.otus.library;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.exceptions.ShellCommandProcessorNotFound;
import ru.otus.library.service.shell.ShellCommandsProcessorImpl;
import ru.otus.library.service.shell.ShellOptionsRegistry;

import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_GENRES;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_GENRES_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_AUTHORS;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_AUTHORS_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_BOOKS;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_BOOKS_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_TITLE;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_TITLE_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_ID;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_ID_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.UPDATE_BOOK;
import static ru.otus.library.service.utils.MenuCommandConstants.UPDATE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.DELETE_BOOK;
import static ru.otus.library.service.utils.MenuCommandConstants.DELETE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.CREATE_BOOK;
import static ru.otus.library.service.utils.MenuCommandConstants.CREATE_BOOK_KEY;


@ShellComponent
public class ApplicationRunner {

    private final ShellOptionsRegistry menuOptionsRegistry;

    private final ShellCommandsProcessorImpl commandsProcessor;

    public ApplicationRunner(ShellOptionsRegistry menuOptionsRegistry, ShellCommandsProcessorImpl commandsProcessor) {
        this.menuOptionsRegistry = menuOptionsRegistry;
        this.commandsProcessor = commandsProcessor;
    }

    @ShellMethod(value = GET_ALL_GENRES, key = { GET_ALL_GENRES_KEY })
    public void getAllGenres() {
        processCommand(GET_ALL_GENRES_KEY);
    }

    @ShellMethod(value = GET_ALL_AUTHORS, key = { GET_ALL_AUTHORS_KEY })
    public void getAllAuthors() {
        processCommand(GET_ALL_AUTHORS_KEY);
    }

    @ShellMethod(value = GET_ALL_BOOKS, key = { GET_ALL_BOOKS_KEY })
    public void getAllBooks() {
        processCommand(GET_ALL_BOOKS_KEY);
    }

    @ShellMethod(value = GET_BOOK_BY_TITLE, key = { GET_BOOK_BY_TITLE_KEY })
    public void getBookByTitle() {
        processCommand(GET_BOOK_BY_TITLE_KEY);
    }

    @ShellMethod(value = GET_BOOK_BY_ID, key = { GET_BOOK_BY_ID_KEY })
    public void getBookById() {
        processCommand(GET_BOOK_BY_ID_KEY);
    }

    @ShellMethod(value = UPDATE_BOOK, key = { UPDATE_BOOK_KEY })
    public void updateBook() {
        processCommand(UPDATE_BOOK_KEY);
    }

    @ShellMethod(value = DELETE_BOOK, key = { DELETE_BOOK_KEY })
    public void bookGenre() {
        processCommand(DELETE_BOOK_KEY);
    }

    @ShellMethod(value = CREATE_BOOK, key = { CREATE_BOOK_KEY })
    public void createBook() {
        processCommand(CREATE_BOOK_KEY);
    }

    private void processCommand(String key) {
        var selectedMenuOption = menuOptionsRegistry.getShellOptionByName(key)
                .orElseThrow(() -> new ShellCommandProcessorNotFound("Menu command processor is not found"));
        commandsProcessor.processShellCommand(selectedMenuOption);
    }
}
