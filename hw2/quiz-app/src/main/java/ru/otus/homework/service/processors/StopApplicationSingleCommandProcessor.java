package ru.otus.homework.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.homework.service.ApplicationStopService;
import ru.otus.homework.service.menu.MenuOption;

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
