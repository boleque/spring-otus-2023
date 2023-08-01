import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.otus.homework.domain.Quiz;
import ru.otus.homework.service.QuizService;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-context.xml" })
public class QuizDaoTest {

    @Autowired
    private QuizService service;

    @Test
    public void getQuizTest() {
        System.out.println("getQuizTest: " + service.toString());
        Quiz quiz = service.getQuiz();
        assertEquals("Quiz002", quiz.getId());
        assertEquals("World geography", quiz.getTopic());
        assertEquals(5, quiz.getQuestions().size());
    }
}
