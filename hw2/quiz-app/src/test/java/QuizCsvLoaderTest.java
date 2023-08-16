import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Quiz;
import ru.otus.homework.service.QuizCsvLoader;
import ru.otus.homework.service.QuizLoader;
import ru.otus.homework.service.QuizParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class QuizCsvLoaderTest {

    private QuizLoader quizLoader;

    @Mock
    private QuizParser quizParser;

    @Mock
    private Resource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.quizLoader = new QuizCsvLoader(resource, quizParser);
    }

    @Test
    public void getQuizTest_Success() throws IOException {
        when(resource.getInputStream()).thenReturn(
                new ByteArrayInputStream(("Quiz01\n" +
                        "World geography\n" +
                        "Q1;What country has the most islands in the world?;Sweden;Estonia;Russia;0").getBytes())
        );
        when(quizParser.parseQuiz(any())).thenReturn(
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
        var quiz = quizLoader.getQuiz();
        assertAll("Quiz",
                () -> assertEquals("Quiz01", quiz.getId()),
                () -> assertEquals("World geography", quiz.getTopic()),
                () -> assertEquals(1, quiz.getQuestions().size())
        );
    }
}
