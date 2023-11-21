package ru.otus.library;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.exceptions.ShellCommandProcessorNotFound;
import ru.otus.library.service.menu.MenuCommandsProcessorImpl;
import ru.otus.library.service.menu.ShellOptionsRegistry;

import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_GENRES_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_GENRES;


@ShellComponent
public class GenreShellRunner {

    private final ShellOptionsRegistry menuOptionsRegistry;

    private final MenuCommandsProcessorImpl commandsProcessor;

    public GenreShellRunner(ShellOptionsRegistry menuOptionsRegistry, MenuCommandsProcessorImpl commandsProcessor) {
        this.menuOptionsRegistry = menuOptionsRegistry;
        this.commandsProcessor = commandsProcessor;
    }

    @ShellMethod(value = GET_ALL_GENRES, key = { GET_ALL_GENRES_KEY })
    public void getAllGenres() {
        processCommand(GET_ALL_GENRES_KEY);
    }

    private void processCommand(String key) {
        var selectedMenuOption = menuOptionsRegistry.getShellOptionByName(key)
                .orElseThrow(() -> new ShellCommandProcessorNotFound("Menu command processor is not found"));
        commandsProcessor.processShellCommand(selectedMenuOption);
    }
}
