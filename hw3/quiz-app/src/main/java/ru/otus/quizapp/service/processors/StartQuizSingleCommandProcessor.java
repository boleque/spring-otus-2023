package ru.otus.quizapp.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.quizapp.converters.Converter;
import ru.otus.quizapp.domain.Question;
import ru.otus.quizapp.domain.User;
import ru.otus.quizapp.domain.Result;
import ru.otus.quizapp.domain.Session;
import ru.otus.quizapp.service.IOService;
import ru.otus.quizapp.service.LocalizationService;
import ru.otus.quizapp.service.QuizService;
import ru.otus.quizapp.service.menu.MenuOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartQuizSingleCommandProcessor implements MenuSingleCommandProcessor {

    private final QuizService quizService;

    private final LocalizationService localizationService;

    private final MenuOption startQuizMenuOption;

    private final Converter converter;

    private final IOService ioService;

    public StartQuizSingleCommandProcessor(
            IOService ioService,
            QuizService quizService,
            LocalizationService localizationService,
            Converter converter,
            MenuOption startQuizMenuOption) {
        this.quizService = quizService;
        this.localizationService = localizationService;
        this.startQuizMenuOption = startQuizMenuOption;
        this.ioService = ioService;
        this.converter = converter;
    }

    @Override
    public void processCommand() {
        var result = doQuiz();
        ioService.outputString(
                converter.convertResultToString(result)
        );
    }

    private Result doQuiz() {
        var quiz = quizService.getQuiz();
        List<Question> questions = quiz.getQuestions();

        var userInfo = askUserInfo();
        var quizStats = askQuestions(questions);

        var session = new Session(userInfo, quiz, quizStats);
        var result = quizService.processQuizSession(session);
        quizService.saveResult(result);
        return result;
    }

    private User askUserInfo() {
        var firstName = ioService.readStringWithPrompt(localizationService.getMessage("enterFirstNameAction"));
        var lastName = ioService.readStringWithPrompt(localizationService.getMessage("enterLastNameAction"));
        return new User(firstName, lastName);
    }

    private Map<String, Integer> askQuestions(List<Question> questions) {
        Map<String, Integer> answers = new HashMap<>();
        for (Question question : questions) {
            var prompt = converter.convertQuiestionToString(question);
            var answerIdx = ioService.readIntWithPrompt(prompt);
            answers.put(question.getId(), answerIdx);
        }
        return answers;
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return startQuizMenuOption;
    }
}
