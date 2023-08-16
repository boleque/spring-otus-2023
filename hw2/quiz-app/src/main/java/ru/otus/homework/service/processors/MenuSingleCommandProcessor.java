package ru.otus.homework.service.processors;

import ru.otus.homework.service.menu.MenuOption;

public interface MenuSingleCommandProcessor {

    void processCommand();

    MenuOption getProcessedCommandOption();
}
