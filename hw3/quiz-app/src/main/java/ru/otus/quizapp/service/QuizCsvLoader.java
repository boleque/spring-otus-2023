package ru.otus.quizapp.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.quizapp.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
public class QuizCsvLoader implements QuizLoader {

    private final Resource resource;

    private final QuizParser quizParser;

    private final LocalizationService localizationService;

    public QuizCsvLoader(Resource resource, QuizParser quizParser, LocalizationService localizationService) {
        this.resource = resource;
        this.quizParser = quizParser;
        this.localizationService = localizationService;
    }

    @Override
    public Quiz getQuiz() {
        try {
            var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            return quizParser.parseQuiz(reader);
        } catch (IOException ex) {
            var msg = localizationService.getMessage("failedToParseQuizResource");
            throw new RuntimeException(msg);
        }
    }
}
