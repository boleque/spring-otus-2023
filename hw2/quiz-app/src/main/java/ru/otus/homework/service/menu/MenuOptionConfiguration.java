package ru.otus.homework.service.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class MenuOptionConfiguration {

    @Bean
    public MenuOption startQuizMenuOption() {
        return new MenuOption(1, "Start quiz");
    }

    @Bean
    public MenuOption quitMenuOption() {
        return new MenuOption(2, "Quit");
    }
}
