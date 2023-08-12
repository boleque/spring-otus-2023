package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.exceptions.MenuCommandProcessorNotFound;
import ru.otus.homework.exceptions.MenuItemIndexOutOfBoundsException;
import ru.otus.homework.service.menu.MenuCommandsProcessor;
import ru.otus.homework.service.menu.MenuOptionsRegistry;
import ru.otus.homework.service.menu.MenuOption;

import java.util.Comparator;

@Service
public class ApplicationRunner {

    private final IOService ioService;

    private final ApplicationStopService applicationStopService;

    private final MenuOptionsRegistry menuOptionsRegistry;

    private final MenuCommandsProcessor commandsProcessor;


    public ApplicationRunner(IOService ioService,
                             ApplicationStopService applicationStopService,
                             MenuOptionsRegistry menuOptionsRegistry,
                             MenuCommandsProcessor commandsProcessor) {
        this.ioService = ioService;
        this.applicationStopService = applicationStopService;
        this.menuOptionsRegistry = menuOptionsRegistry;
        this.commandsProcessor = commandsProcessor;
    }

    public void run() {
        while (applicationStopService.isApplicationRunning()) {
            outputMenu();
            try {
                var selectedMenuItem = readSelectedOptionNumber();
                processMenuCommand(selectedMenuItem);

            } catch (NumberFormatException e) {
                ioService.outputString("Wrong input number");
            } catch (MenuItemIndexOutOfBoundsException e) {
                ioService.outputString("Wrong option number");
            } catch (MenuCommandProcessorNotFound e) {
                ioService.outputString("Menu action handler is not found");
            }
        }
    }

    private void outputMenu() {
        ioService.outputString("Choose action...");
        menuOptionsRegistry.getAvailableMenuOptions().stream()
                .sorted(Comparator.comparingInt(MenuOption::getId))
                .map(m -> m.getId() + ". " + m.getDescription())
                .forEach(ioService::outputString);
    }

    private void processMenuCommand(int selectedMenuItemId) {
        var selectedMenuOption = menuOptionsRegistry.getMenuOptionById(selectedMenuItemId)
                .orElseThrow(() -> new MenuItemIndexOutOfBoundsException("Given menu item index is out of range"));

        commandsProcessor.processMenuCommand(selectedMenuOption);
    }

    private int readSelectedOptionNumber() {
        return ioService.readInt();
    }
}
