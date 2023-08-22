package ru.otus.quizapp.service.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.quizapp.service.LocalizationService;


@Component
public class MenuOptionConfiguration {

    private final LocalizationService localizationService;

    public MenuOptionConfiguration(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Bean
    public MenuOption startQuizMenuOption() {
        var description = localizationService.getMessage("startQuizMenuOption");
        return new MenuOption(1, description);
    }

    @Bean
    public MenuOption quitMenuOption() {
        var description = localizationService.getMessage("quitQuizMenuOption");
        return new MenuOption(2, description);
    }
}
