package ru.otus.quizapp.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.quizapp.service.ApplicationStopService;
import ru.otus.quizapp.service.menu.MenuOption;

@Component
public class StopApplicationSingleCommandProcessor implements MenuSingleCommandProcessor {

    private final MenuOption quitMenuOption;

    private final ApplicationStopService applicationStopService;

    public StopApplicationSingleCommandProcessor(ApplicationStopService applicationStopService,
                                                 MenuOption quitMenuOption) {
        this.applicationStopService = applicationStopService;
        this.quitMenuOption = quitMenuOption;
    }

    @Override
    public void processCommand() {
        applicationStopService.stopApplication();
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return quitMenuOption;
    }
}
