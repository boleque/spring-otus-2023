package ru.otus.homework.service.menu;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class MenuOptionsRegistryImpl implements MenuOptionsRegistry {

    private List<MenuOption> availableMenuOptions;

    public MenuOptionsRegistryImpl(List<MenuOption> availableMenuOptions) {
        this.availableMenuOptions = availableMenuOptions;
    }

    @Override
    public List<MenuOption> getAvailableMenuOptions() {
        return availableMenuOptions;
    }

    @Override
    public Optional<MenuOption> getMenuOptionById(int id) {
        for (MenuOption option : availableMenuOptions) {
            if (option.getId() == id) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }
}
