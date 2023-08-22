package ru.otus.quizapp.converters;

import ru.otus.quizapp.domain.Result;

public interface ResultConverter {
    String convertResultToString(Result result);
}
