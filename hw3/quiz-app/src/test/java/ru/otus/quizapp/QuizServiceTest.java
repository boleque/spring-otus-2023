package ru.otus.quizapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.otus.quizapp.domain.Session;
import ru.otus.quizapp.domain.User;
import ru.otus.quizapp.service.QuizService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@ContextConfiguration(classes = { QuizTestConfig.class })
public class QuizServiceTest {

    @Autowired
    private QuizService quizTestService;

    @Test
    public void processQuizSession_Passed() {
        var quiz = quizTestService.getQuiz();
        var user = new User("Joe", "Bloggins");
        var answers = Map.of(
                "Q1", 0,
                "Q2", 1,
                "Q3", 2,
                "Q4", 0,
                "Q5", 1
        );
        var session = new Session(user, quiz, answers);
        var result = quizTestService.processQuizSession(session);
        assertEquals(true, result.isPassed());
    }

    @Test
    public void processQuizSession_Failed() {
        var quiz = quizTestService.getQuiz();
        var user = new User("Joe", "Bloggins");
        var answers = Map.of(
                "Q1", 1,
                "Q2", 1,
                "Q3", 0,
                "Q4", 1,
                "Q5", 1
        );
        var session = new Session(user, quiz, answers);
        var result = quizTestService.processQuizSession(session);
        assertEquals(false, result.isPassed());
    }
}
