package ru.otus.quizapp.exceptions;

public class MenuItemIndexOutOfBoundsException extends IndexOutOfBoundsException {

    private final String message = "menuItemIndexOutOfBoundsException";

    public MenuItemIndexOutOfBoundsException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
