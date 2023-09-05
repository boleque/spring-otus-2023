package ru.otus.library.service.menu;


import org.springframework.stereotype.Component;
import ru.otus.library.exceptions.ShellCommandProcessorNotFound;
import ru.otus.library.service.processors.CommandProcessor;

import java.util.List;


@Component
public class MenuCommandsProcessorImpl implements MenuCommandsProcessor {

    private final List<CommandProcessor> processors;

    public MenuCommandsProcessorImpl(List<CommandProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void processShellCommand(MenuOption selectedMenuOption) {
        processors.stream()
                .filter(processor -> processor.getProcessedCommandOption().equals(selectedMenuOption))
                .findFirst()
                .ifPresentOrElse(
                        CommandProcessor::processCommand,
                        () -> {
                            throw new ShellCommandProcessorNotFound("Processor is not registered");
                        }
                );
    }
}
