package ru.otus.library.service.shell;


import org.springframework.stereotype.Component;
import ru.otus.library.exceptions.ShellCommandProcessorNotFound;
import ru.otus.library.service.processors.CommandProcessor;

import java.util.List;


@Component
public class ShellCommandsProcessorImpl implements ShellCommandsProcessor {

    private final List<CommandProcessor> processors;

    public ShellCommandsProcessorImpl(List<CommandProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void processShellCommand(ShellOption selectedMenuOption) {
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
