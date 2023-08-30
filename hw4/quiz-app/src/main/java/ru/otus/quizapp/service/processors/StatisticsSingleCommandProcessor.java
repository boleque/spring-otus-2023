package ru.otus.quizapp.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.quizapp.converters.Converter;
import ru.otus.quizapp.service.IOService;
import ru.otus.quizapp.service.QuizService;


@Component
public class StatisticsSingleCommandProcessor implements CommandProcessor {

    private final QuizService quizService;

    private final Converter converter;

    private final IOService ioService;

    public StatisticsSingleCommandProcessor(
            IOService ioService,
            QuizService quizService,
            Converter converter) {
        this.quizService = quizService;
        this.ioService = ioService;
        this.converter = converter;
    }

    @Override
    public void processCommand() {
        var results = quizService.getResults();
        ioService.outputString(converter.convertResultsToString(results));
    }
}
