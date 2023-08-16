import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.homework.config.AppSettings;
import ru.otus.homework.dao.QuizDao;
import ru.otus.homework.dao.QuizDaoImpl;
import ru.otus.homework.dao.ResultDao;
import ru.otus.homework.dao.ResultDaoImpl;
import ru.otus.homework.service.*;

@Component
public class QuizServiceConfig {

    @Bean
    public QuizService quizTestService() {
        AppSettings settings = new AppSettings(false, 0.6);
        ResultDao resultDao = new ResultDaoImpl();
        QuizParser parser = new QuizParserImpl();
        QuizLoader loader = new QuizCsvLoader(settings.resource("world_geography.csv"), parser);
        QuizDao quizDao = new QuizDaoImpl(loader);
        return new QuizServiceImpl(quizDao, resultDao, settings);
    }
}
