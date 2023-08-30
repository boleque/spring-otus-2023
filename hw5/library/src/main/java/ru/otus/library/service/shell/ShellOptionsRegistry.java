package ru.otus.library.service.shell;

import java.util.List;
import java.util.Optional;

public interface ShellOptionsRegistry {

    List<ShellOption> getAvailableShellOptions();

    Optional<ShellOption> getShellOptionById(int id);

    Optional<ShellOption> getShellOptionByName(String name);
}
