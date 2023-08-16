package ru.otus.homework.service;

import ru.otus.homework.domain.Quiz;

import java.io.BufferedReader;

public interface QuizParser {

    Quiz parseQuiz(BufferedReader reader);
}
