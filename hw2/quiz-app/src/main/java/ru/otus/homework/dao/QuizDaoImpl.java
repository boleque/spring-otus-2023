package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Quiz;
import ru.otus.homework.service.QuizLoader;

@Repository
public class QuizDaoImpl implements QuizDao {

    private final QuizLoader quizLoader;

    public QuizDaoImpl(QuizLoader quizLoader) {
        this.quizLoader = quizLoader;
    }

    @Override
    public Quiz getQuiz() {
        return quizLoader.getQuiz();
    }
}
