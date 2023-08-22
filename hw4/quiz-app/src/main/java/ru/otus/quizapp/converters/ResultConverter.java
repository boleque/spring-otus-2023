package ru.otus.quizapp.converters;

import ru.otus.quizapp.domain.Result;

import java.util.List;

public interface ResultConverter {
    String convertResultToString(Result result);

    String convertResultsToString(List<Result> results);
}
