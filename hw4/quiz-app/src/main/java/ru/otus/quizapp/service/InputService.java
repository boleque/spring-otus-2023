package ru.otus.quizapp.service;

public interface InputService {
    int readInt();

    int readIntWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);
}
