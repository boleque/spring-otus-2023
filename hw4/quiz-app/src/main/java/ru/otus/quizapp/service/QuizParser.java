package ru.otus.quizapp.service;

import ru.otus.quizapp.domain.Quiz;

import java.io.BufferedReader;

public interface QuizParser {

    Quiz parseQuiz(BufferedReader reader);
}
