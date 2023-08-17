package ru.otus.quizapp.applicationrunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.quizapp.exceptions.MenuCommandProcessorNotFound;
import ru.otus.quizapp.exceptions.MenuItemIndexOutOfBoundsException;
import ru.otus.quizapp.service.ApplicationStopService;
import ru.otus.quizapp.service.IOService;
import ru.otus.quizapp.service.LocalizationService;
import ru.otus.quizapp.service.menu.MenuCommandsProcessor;
import ru.otus.quizapp.service.menu.MenuOptionsRegistry;
import ru.otus.quizapp.service.menu.MenuOption;

import java.util.Comparator;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final IOService ioService;

    private final ApplicationStopService applicationStopService;

    private final MenuOptionsRegistry menuOptionsRegistry;

    private final MenuCommandsProcessor commandsProcessor;

    private final LocalizationService localizationService;

    public ApplicationRunner(IOService ioService,
                             ApplicationStopService applicationStopService,
                             MenuOptionsRegistry menuOptionsRegistry,
                             MenuCommandsProcessor commandsProcessor,
                             LocalizationService localizationService) {
        this.ioService = ioService;
        this.applicationStopService = applicationStopService;
        this.menuOptionsRegistry = menuOptionsRegistry;
        this.commandsProcessor = commandsProcessor;
        this.localizationService = localizationService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (applicationStopService.isApplicationRunning()) {
            outputMenu();
            try {
                var selectedMenuItem = readSelectedOptionNumber();
                processMenuCommand(selectedMenuItem);
            } catch (NumberFormatException e) {
                var msg = localizationService.getMessage("numberFormatException");
                ioService.outputString(msg);
            } catch (MenuItemIndexOutOfBoundsException e) {
                var msg = localizationService.getMessage("menuItemIndexOutOfBoundsException");
                ioService.outputString(msg);
            } catch (MenuCommandProcessorNotFound e) {
                var msg = localizationService.getMessage("menuCommandProcessorNotFound");
                ioService.outputString(msg);
            }
        }
    }


    private void outputMenu() {
        var chooseAction = localizationService.getMessage("chooseAction");
        ioService.outputString(chooseAction);
        menuOptionsRegistry.getAvailableMenuOptions().stream()
                .sorted(Comparator.comparingInt(MenuOption::getId))
                .map(m -> m.getId() + ". " + m.getDescription())
                .forEach(ioService::outputString);
    }

    private void processMenuCommand(int selectedMenuItemId) {
        var selectedMenuOption = menuOptionsRegistry.getMenuOptionById(selectedMenuItemId)
                .orElseThrow(() -> {
                    var msg = localizationService.getMessage("numberFormatException");
                    return new MenuItemIndexOutOfBoundsException(msg);
                }
        );

        commandsProcessor.processMenuCommand(selectedMenuOption);
    }

    private int readSelectedOptionNumber() {
        return ioService.readInt();
    }
}
