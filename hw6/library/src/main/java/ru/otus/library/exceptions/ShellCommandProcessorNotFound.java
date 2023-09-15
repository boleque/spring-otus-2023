package ru.otus.library.exceptions;

public class ShellCommandProcessorNotFound extends RuntimeException {
    public ShellCommandProcessorNotFound(String message) {
        super(message);
    }
}
