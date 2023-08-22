package ru.otus.quizapp.dao;

import org.springframework.stereotype.Component;
import ru.otus.quizapp.domain.Quiz;
import ru.otus.quizapp.service.QuizLoader;

@Component
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
