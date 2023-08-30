package ru.otus.quizapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.quizapp.domain.Question;
import ru.otus.quizapp.domain.Quiz;
import ru.otus.quizapp.service.LocalizationService;
import ru.otus.quizapp.service.QuizCsvLoader;
import ru.otus.quizapp.service.QuizLoader;
import ru.otus.quizapp.service.QuizParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@SpringBootTest(classes = QuizCsvLoader.class)
@ExtendWith({SpringExtension.class})
public class QuizCsvLoaderTest {

    @Autowired
    private QuizLoader quizLoader;

    @MockBean
    private QuizParser quizParser;

    @MockBean
    private Resource resource;

    @MockBean
    LocalizationService localizationService;

    @BeforeEach
    void setUp() throws IOException {
        given(resource.getInputStream()).willReturn(
                new ByteArrayInputStream(("Quiz01\n" +
                        "World geography\n" +
                        "Q1;What country has the most islands in the world?;Sweden;Estonia;Russia;0").getBytes())
        );
        given(quizParser.parseQuiz(any())).willReturn(
                new Quiz(
                        "Quiz01",
                        "World geography",
                        List.of(
                                new Question(
                                        "Q1",
                                        "Quiz01",
                                        "What country has the most islands in the world?",
                                        List.of()
                                )
                        )
                )
        );
    }

    @Test
    @DisplayName("Loader is capable to return ")
    public void getQuizTest_Success() throws IOException {
        var quiz = quizLoader.getQuiz();
        assertAll("Quiz",
                () -> assertEquals("Quiz01", quiz.getId()),
                () -> assertEquals("World geography", quiz.getTopic()),
                () -> assertEquals(1, quiz.getQuestions().size())
        );
    }
}
