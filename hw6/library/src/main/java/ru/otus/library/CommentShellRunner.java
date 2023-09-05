package ru.otus.library;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.exceptions.ShellCommandProcessorNotFound;
import ru.otus.library.service.menu.MenuCommandsProcessorImpl;
import ru.otus.library.service.menu.ShellOptionsRegistry;

import static ru.otus.library.service.utils.MenuCommandConstants.GET_COMMENT_BY_ID;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_COMMENT_BY_ID_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_COMMENT_BY_BOOK_ID;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_COMMENT_BY_BOOK_ID_KEY;


@ShellComponent
public class CommentShellRunner {

    private final ShellOptionsRegistry menuOptionsRegistry;

    private final MenuCommandsProcessorImpl commandsProcessor;

    public CommentShellRunner(ShellOptionsRegistry menuOptionsRegistry, MenuCommandsProcessorImpl commandsProcessor) {
        this.menuOptionsRegistry = menuOptionsRegistry;
        this.commandsProcessor = commandsProcessor;
    }

    @ShellMethod(value = GET_COMMENT_BY_ID, key = { GET_COMMENT_BY_ID_KEY })
    public void getCommentById() {
        processCommand(GET_COMMENT_BY_ID_KEY);
    }

    @ShellMethod(value = GET_COMMENT_BY_BOOK_ID, key = { GET_COMMENT_BY_BOOK_ID_KEY })
    public void getCommentsByBookId() {
        processCommand(GET_COMMENT_BY_BOOK_ID_KEY);
    }

    private void processCommand(String key) {
        var selectedMenuOption = menuOptionsRegistry.getShellOptionByName(key)
                .orElseThrow(() -> new ShellCommandProcessorNotFound("Menu command processor is not found"));
        commandsProcessor.processShellCommand(selectedMenuOption);
    }
}
