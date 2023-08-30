package ru.otus.library.service.shell;


public class ShellOption {

    private final int id;

    private final String name;

    public ShellOption(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShellOption that = (ShellOption) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
