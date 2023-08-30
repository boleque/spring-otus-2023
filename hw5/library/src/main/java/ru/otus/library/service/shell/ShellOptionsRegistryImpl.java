package ru.otus.library.service.shell;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class ShellOptionsRegistryImpl implements ShellOptionsRegistry {

    private List<ShellOption> availableShellOptions;

    private Map<String, ShellOption> availableShellOptionsMap;

    public ShellOptionsRegistryImpl(List<ShellOption> availableMenuOptions) {
        this.availableShellOptionsMap = availableMenuOptions.stream()
                .collect(Collectors.toMap(ShellOption::getName, item -> item));
        this.availableShellOptions = availableMenuOptions;
    }

    @Override
    public List<ShellOption> getAvailableShellOptions() {
        return availableShellOptions;
    }

    @Override
    public Optional<ShellOption> getShellOptionById(int id) {
        for (ShellOption option : availableShellOptions) {
            if (option.getId() == id) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShellOption> getShellOptionByName(String name) {
        return Optional.ofNullable(availableShellOptionsMap.get(name));
    }
}
