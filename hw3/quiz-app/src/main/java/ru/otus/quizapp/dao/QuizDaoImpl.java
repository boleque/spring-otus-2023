package ru.otus.quizapp.dao;

import org.springframework.stereotype.Repository;
import ru.otus.quizapp.domain.Quiz;
import ru.otus.quizapp.service.QuizLoader;

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
