package ru.otus.quizapp.service;


import ru.otus.quizapp.domain.Quiz;
import ru.otus.quizapp.domain.Result;
import ru.otus.quizapp.domain.Session;

public interface QuizService {

    Quiz getQuiz();

    Result processQuizSession(Session session);

    void saveResult(Result result);

}
