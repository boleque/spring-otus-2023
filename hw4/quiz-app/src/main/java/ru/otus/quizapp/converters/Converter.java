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

        var firstNameField = localizationService.getMessage("firstNameQuizResultConverter");
        var lastNameField = localizationService.getMessage("lastNameQuizResultConverter");
        var quizTopicField = localizationService.getMessage("quizTopicQuizResultConverter");
        var quizTopicValue = localizationService.getMessage(quizInfo.getTopic());
        var passedField = localizationService.getMessage("passedQuizResultConverter");
        var yesAnswerValue = localizationService.getMessage("yesAnswerQuizResultConverter");
        var noAnswerValue = localizationService.getMessage("noAnsewerQuizResultConverter");

        return String.format(
                "----------------\n%s: %s\n%s: %s\n%s: %s\n%s: %s\n----------------",
                firstNameField,
                userInfo.getFirstName(),
                lastNameField,
                userInfo.getLastName(),
                quizTopicField,
                quizTopicValue,
                passedField,
                isPassed ? yesAnswerValue : noAnswerValue
        );
    }

    @Override
    public String convertResultsToString(List<Result> results) {
        if (results.isEmpty()) {
            return "There are no statistics";
        }
        var testStatisticsMsg = "Test Statistics";
        var totalResultsMsg = "Quiz completed";
        var passedNumberMsg = "Passed";
        var totalResults = results.size();
        var passedNumber = results.stream().filter(Result::isPassed);
        return String.format(
                "\n%s\n%s: %d\n%s: %d\n%s: %d",
                testStatisticsMsg,
                totalResultsMsg,
                totalResults,
                passedNumberMsg,
                passedNumber
        );
    }
}
