package ru.otus.homework.service.menu;


import org.springframework.stereotype.Component;
import ru.otus.homework.exceptions.MenuCommandProcessorNotFound;
import ru.otus.homework.service.processors.MenuSingleCommandProcessor;

import java.util.List;


@Component
public class MenuCommandsProcessorImpl implements MenuCommandsProcessor {

    private List<MenuSingleCommandProcessor> processors;

    public MenuCommandsProcessorImpl(List<MenuSingleCommandProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void processMenuCommand(MenuOption selectedMenuOption) {
        processors.stream()
                .filter(processor -> processor.getProcessedCommandOption().equals(selectedMenuOption))
                .findFirst()
                .ifPresentOrElse(
                        MenuSingleCommandProcessor::processCommand,
                        () -> {
                            throw new MenuCommandProcessorNotFound("Processor is not registered");
                        }
                );
    }
}
