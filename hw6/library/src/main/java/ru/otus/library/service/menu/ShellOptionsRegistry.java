package ru.otus.library.service.menu;

import java.util.List;
import java.util.Optional;

public interface ShellOptionsRegistry {

    List<MenuOption> getAvailableShellOptions();

    Optional<MenuOption> getShellOptionById(int id);

    Optional<MenuOption> getShellOptionByName(String name);
}
