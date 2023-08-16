import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import ru.otus.homework.domain.Quiz;
import ru.otus.homework.service.QuizCsvLoader;
import ru.otus.homework.service.QuizLoader;
import ru.otus.homework.service.QuizParser;
import ru.otus.homework.service.QuizParserImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuizParserTest {

    private QuizParser quizParser;

    @BeforeEach
    void setUp() {
        quizParser = new QuizParserImpl();
    }

    @Test
    public void parseQuizTest_Success() throws IOException {
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
        var byteArray = new ByteArrayInputStream(
                (
                        "Quiz01\n" +
                                "World geography\n" +
                                "Q1;What country has the most islands in the world?;Estonia;Russia;0"
                ).getBytes()
        );
        var bufferReader = new BufferedReader(new InputStreamReader(byteArray));
        var exception = assertThrows(RuntimeException.class,
                () -> quizParser.parseQuiz(bufferReader)
        );
        assertEquals("Failed to parse quiz file source", exception.getMessage());
    }
}
