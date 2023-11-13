package ru.otus.library;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.exceptions.ShellCommandProcessorNotFound;
import ru.otus.library.service.menu.MenuCommandsProcessorImpl;
import ru.otus.library.service.menu.ShellOptionsRegistry;


import static ru.otus.library.service.utils.MenuCommandConstants.MIGRATE_MONGO_TO_H2;
import static ru.otus.library.service.utils.MenuCommandConstants.MIGRATE_MONGO_TO_H2_KEY;


@RequiredArgsConstructor
@ShellComponent
public class DbMigrationShellRunner {

    private final ShellOptionsRegistry menuOptionsRegistry;

    private final MenuCommandsProcessorImpl commandsProcessor;

    @ShellMethod(value = MIGRATE_MONGO_TO_H2, key = { MIGRATE_MONGO_TO_H2_KEY })
    public void migrateMongoToH2() {
        processCommand(MIGRATE_MONGO_TO_H2_KEY);
    }

    private void processCommand(String key) {
        var selectedMenuOption = menuOptionsRegistry.getShellOptionByName(key)
                .orElseThrow(() -> new ShellCommandProcessorNotFound("Menu command processor is not found"));
        commandsProcessor.processShellCommand(selectedMenuOption);
    }
}
