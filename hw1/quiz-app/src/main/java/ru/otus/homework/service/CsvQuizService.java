package ru.otus.homework.service;

import ru.otus.homework.dao.QuizDao;
import ru.otus.homework.domain.Quiz;

public class CsvQuizService implements QuizService {

    private final QuizDao dao;

    public CsvQuizService(QuizDao dao) {
        this.dao = dao;
    }

    @Override
    public Quiz getQuiz() {
        return dao.getQuiz();
    }
}
