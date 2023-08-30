package ru.otus.quizapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.quizapp.domain.Session;
import ru.otus.quizapp.domain.User;
import ru.otus.quizapp.service.QuizService;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;


@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith({SpringExtension.class})
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Test
    public void processQuizSession_Passed() {
        var quiz = quizService.getQuiz();
        var user = new User("Joe", "Bloggins");
        var answers = Map.of(
                "Q1", 1,
                "Q2", 2,
                "Q3", 3,
                "Q4", 1,
                "Q5", 2
        );
        var session = new Session(user, quiz, answers);
        var result = quizService.processQuizSession(session);
        assertTrue(result.isPassed());
    }

    @Test
    public void processQuizSession_Failed() {
        var quiz = quizService.getQuiz();
        var user = new User("Joe", "Bloggins");
        var answers = Map.of(
                "Q1", 2,
                "Q2", 2,
                "Q3", 1,
                "Q4", 2,
                "Q5", 2
        );
        var session = new Session(user, quiz, answers);
        var result = quizService.processQuizSession(session);
        assertFalse(result.isPassed());
    }
}
