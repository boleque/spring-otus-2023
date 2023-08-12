import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import ru.otus.homework.service.QuizCsvLoader;
import ru.otus.homework.service.QuizLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QuizCsvLoaderTest {

    private QuizLoader quizLoader;

    @Mock
    private Resource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.quizLoader = new QuizCsvLoader(resource);
    }

    @Test
    public void getQuizTest_Success() throws IOException {
        when(resource.getInputStream()).thenReturn(
                new ByteArrayInputStream(("Quiz01\n" +
                        "World geography\n" +
                        "Q1;What country has the most islands in the world?;Sweden;Estonia;Russia;0").getBytes())
        );
        var quiz = quizLoader.getQuiz();
        assertAll("Quiz",
                () -> assertEquals("Quiz01", quiz.getId()),
                () -> assertEquals("World geography", quiz.getTopic()),
                () -> assertEquals(1, quiz.getQuestions().size())
        );
    }

    @Test
    public void getQuizTest_Failed() throws IOException {
        when(resource.getInputStream()).thenReturn(
                new ByteArrayInputStream(("Quiz01\n" +
                        "World geography\n" +
                        "Q1;What country has the most islands in the world?;Sweden;0").getBytes())
        );
        var exception = assertThrows(RuntimeException.class,
                () -> quizLoader.getQuiz()
        );
        assertEquals("Failed to parse quiz file source", exception.getMessage());
    }
}
