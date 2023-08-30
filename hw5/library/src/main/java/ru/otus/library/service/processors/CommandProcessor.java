package ru.otus.library.service.processors;


import ru.otus.library.service.shell.ShellOption;

public interface CommandProcessor {

    void processCommand();

    ShellOption getProcessedCommandOption();
}
