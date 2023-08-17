package ru.otus.quizapp.service.processors;

import ru.otus.quizapp.service.menu.MenuOption;

public interface MenuSingleCommandProcessor {

    void processCommand();

    MenuOption getProcessedCommandOption();
}
