package ru.otus.library.service.processors;


import ru.otus.library.service.menu.MenuOption;

public interface CommandProcessor {

    void processCommand();

    MenuOption getProcessedCommandOption();
}
