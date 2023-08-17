package ru.otus.quizapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.quizapp.service.LocalizationService;
import ru.otus.quizapp.service.QuizParser;
import ru.otus.quizapp.service.QuizParserImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizParserTest {

    private QuizParser quizParser;

    private LocalizationService localizationService;

    @BeforeEach
    void setUp() {
        localizationService = mock(LocalizationService.class);
        quizParser = new QuizParserImpl(localizationService);
    }

    @Test
    public void parseQuizTest_Success() throws IOException {
        when(localizationService.getMessage(anyString())).thenReturn(
                "Failed to parse quiz file source"
        );
        var byteArray = new ByteArrayInputStream(
                (
                        "Quiz01\n" +
                        "World geography\n" +
                        "Q1;What country has the most islands in the world?;Sweden;Estonia;Russia;0"
                ).getBytes()
        );
        var bufferReader = new BufferedReader(new InputStreamReader(byteArray));
        var quiz = quizParser.parseQuiz(bufferReader);
        assertAll("Quiz",
                () -> assertEquals("Quiz01", quiz.getId()),
                () -> assertEquals("World geography", quiz.getTopic()),
                () -> assertEquals(1, quiz.getQuestions().size())
        );
    }

    @Test
    public void parseQuizTest_Failed() throws IOException {
        when(localizationService.getMessage(anyString())).thenReturn(
                "Failed to parse quiz file source"
        );
        var byteArray = new ByteArrayInputStream(
                (
                        "Quiz01\n" +
                                "World geography\n" +
                                "Q1;What country has the most islands in the world?;Estonia;Russia;0"
                ).getBytes()
        );
        localizationService.getMessage(anyString());
        var bufferReader = new BufferedReader(new InputStreamReader(byteArray));
        var exception = assertThrows(RuntimeException.class,
                () -> quizParser.parseQuiz(bufferReader)
        );
        assertEquals("Failed to parse quiz file source", exception.getMessage());
    }
}
