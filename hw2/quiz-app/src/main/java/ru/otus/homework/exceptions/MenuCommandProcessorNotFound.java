package ru.otus.homework.exceptions;

public class MenuCommandProcessorNotFound extends RuntimeException {
    public MenuCommandProcessorNotFound(String message) {
        super(message);
    }
}
