package ru.otus.library.service;

public interface InputService {
    int readInt();

    long readLong();

    int readIntWithPrompt(String prompt);

    long readLongWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);
}
