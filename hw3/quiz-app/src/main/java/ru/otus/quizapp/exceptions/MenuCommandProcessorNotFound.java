package ru.otus.quizapp.exceptions;

public class MenuCommandProcessorNotFound extends RuntimeException {

    private final static String MESSAGE = "menuCommandProcessorNotFound";

    public MenuCommandProcessorNotFound(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
