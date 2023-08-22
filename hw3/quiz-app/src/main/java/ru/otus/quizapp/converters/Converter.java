package ru.otus.quizapp.converters;

import org.springframework.stereotype.Service;
import ru.otus.quizapp.domain.Result;
import ru.otus.quizapp.domain.Answer;
import ru.otus.quizapp.domain.Question;
import ru.otus.quizapp.service.LocalizationService;

import java.util.List;

@Service
public class Converter implements QuiestionConverter, ResultConverter  {

    private final LocalizationService localizationService;

    public Converter(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Override
    public String convertQuiestionToString(Question question) {
        List<Answer> answers = question.getAnswers();
        return String.format(
                "%s:\n1. %s\n2. %s\n3. %s",
                localizationService.getMessage(question.getText()),
                localizationService.getMessage(answers.get(0).getText()),
                localizationService.getMessage(answers.get(1).getText()),
                localizationService.getMessage(answers.get(2).getText())
        );
    }

    @Override
    public String convertResultToString(Result result) {
        var session = result.getSession();
        var isPassed = result.isPassed();
        var userInfo = session.getUser();
        var quizInfo = session.getQuiz();

        var firstName = localizationService.getMessage("firstNameQuizResultConverter");
        var lastName = localizationService.getMessage("lastNameQuizResultConverter");
        var quizTopic = localizationService.getMessage("quizTopicQuizResultConverter");
        var passed = localizationService.getMessage("passedQuizResultConverter");
        var yesAnswer = localizationService.getMessage("yesAnswerQuizResultConverter");
        var noAnsewer = localizationService.getMessage("noAnsewerQuizResultConverter");

        return String.format(
                "----------------\n%s: %s\n%s: %s\n%s: %s\n%s: %s\n----------------",
                firstName,
                lastName,
                quizTopic,
                passed,
                userInfo.getFirstName(),
                userInfo.getLastName(),
                quizInfo.getTopic(),
                isPassed ? yesAnswer : noAnsewer
        );
    }
}
