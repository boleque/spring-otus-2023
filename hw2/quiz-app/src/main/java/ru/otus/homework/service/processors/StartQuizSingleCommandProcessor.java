package ru.otus.homework.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.homework.converters.Converter;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.User;
import ru.otus.homework.domain.Result;
import ru.otus.homework.domain.Session;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.QuizService;
import ru.otus.homework.service.menu.MenuOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartQuizSingleCommandProcessor implements MenuSingleCommandProcessor {

    private final QuizService quizService;

    private final MenuOption startQuizMenuOption;

    private final Converter converter;

    private final IOService ioService;

    public StartQuizSingleCommandProcessor(
            IOService ioService,
            QuizService quizService,
            Converter converter,
            MenuOption startQuizMenuOption) {
        this.quizService = quizService;
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
        var firstName = ioService.readStringWithPrompt("Enter first name...");
        var lastName = ioService.readStringWithPrompt("Enter last name...");
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
