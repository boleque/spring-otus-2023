package ru.otus.homework.converters;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Result;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class Converter implements QuiestionConverter, ResultConverter  {

    @Override
    public String convertQuiestionToString(Question question) {
        List<Answer> answers = question.getAnswers();
        return String.format(
                "%s:\n1. %s\n2. %s\n3. %s",
                question.getText(),
                answers.get(0).getText(),
                answers.get(1).getText(),
                answers.get(2).getText()
        );
    }

    @Override
    public String convertResultToString(Result result) {
        var session = result.getSession();
        var isPassed = result.isPassed();
        var userInfo = session.getUser();
        var quizInfo = session.getQuiz();
        return String.format(
                "----------------\nFirst name: %s\nLast name: %s\nQuiz topic: %s\nPassed: %s\n----------------",
                userInfo.getFirstName(),
                userInfo.getLastName(),
                quizInfo.getTopic(),
                isPassed ? "yes" : "no"
        );
    }
}
