package ru.otus.library.service.menu;


public class MenuOption {

    private final int id;

    private final String name;

    public MenuOption(int id, String name) {
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

        MenuOption that = (MenuOption) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
