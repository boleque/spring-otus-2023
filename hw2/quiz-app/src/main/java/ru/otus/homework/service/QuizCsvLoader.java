package ru.otus.homework.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
public class QuizCsvLoader implements QuizLoader {

    private final Resource resource;

    private final QuizParser quizParser;

    public QuizCsvLoader(Resource resource, QuizParser quizParser) {
        this.resource = resource;
        this.quizParser = quizParser;
    }

    @Override
    public Quiz getQuiz() {
        try {
            var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            return quizParser.parseQuiz(reader);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse quiz file source");
        }
    }
}
