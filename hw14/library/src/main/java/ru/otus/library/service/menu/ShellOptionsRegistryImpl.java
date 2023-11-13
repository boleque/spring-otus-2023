package ru.otus.library.service.menu;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class ShellOptionsRegistryImpl implements ShellOptionsRegistry {

    private List<MenuOption> availableShellOptions;

    private Map<String, MenuOption> availableShellOptionsMap;

    public ShellOptionsRegistryImpl(List<MenuOption> availableMenuOptions) {
        this.availableShellOptionsMap = availableMenuOptions.stream()
                .collect(Collectors.toMap(MenuOption::getName, item -> item));
        this.availableShellOptions = availableMenuOptions;
    }

    @Override
    public List<MenuOption> getAvailableShellOptions() {
        return availableShellOptions;
    }

    @Override
    public Optional<MenuOption> getShellOptionById(int id) {
        for (MenuOption option : availableShellOptions) {
            if (option.getId() == id) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<MenuOption> getShellOptionByName(String name) {
        return Optional.ofNullable(availableShellOptionsMap.get(name));
    }
}
