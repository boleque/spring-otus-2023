package ru.otus.homework.service;


import ru.otus.homework.domain.Quiz;
import ru.otus.homework.domain.Result;
import ru.otus.homework.domain.Session;

public interface QuizService {

    Quiz getQuiz();

    Result processQuizSession(Session session);

    void saveResult(Result result);

}
