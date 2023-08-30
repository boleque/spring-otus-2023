package ru.otus.quizapp.applicationrunner;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.quizapp.service.processors.QuizSingleCommandProcessor;
import ru.otus.quizapp.service.processors.StatisticsSingleCommandProcessor;


@ShellComponent
public class ApplicationRunner {

    private final QuizSingleCommandProcessor quizSingleCommandProcessor;

    private final StatisticsSingleCommandProcessor statisticsSingleCommandProcessor;

    public ApplicationRunner(
            QuizSingleCommandProcessor quizSingleCommandProcessor,
            StatisticsSingleCommandProcessor statisticsSingleCommandProcessor) {
        this.quizSingleCommandProcessor = quizSingleCommandProcessor;
        this.statisticsSingleCommandProcessor = statisticsSingleCommandProcessor;
    }

    @ShellMethod(value = "Start quiz", key = {"quiz", "q"})
    public void start() throws Exception {
        quizSingleCommandProcessor.processCommand();
    }

    @ShellMethod(value = "Get statistics", key = {"stats", "s"})
    public void statistics() throws Exception {
        statisticsSingleCommandProcessor.processCommand();
    }
}
