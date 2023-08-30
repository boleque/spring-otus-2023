package ru.otus.quizapp.service;

public interface LocalizationService {
    String getMessage(String key, Object ...args);
}
