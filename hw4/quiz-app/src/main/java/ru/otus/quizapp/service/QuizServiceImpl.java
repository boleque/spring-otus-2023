package ru.otus.quizapp.service;

import org.springframework.stereotype.Service;
import ru.otus.quizapp.config.ApplicationProperties;
import ru.otus.quizapp.dao.QuizDao;
import ru.otus.quizapp.dao.ResultDao;
import ru.otus.quizapp.domain.Quiz;
import ru.otus.quizapp.domain.Session;
import ru.otus.quizapp.domain.Result;
import ru.otus.quizapp.domain.Question;

import java.util.List;


@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    private final ResultDao resultDao;

    private final ApplicationProperties properties;

    public QuizServiceImpl(QuizDao quizDao, ResultDao resultDao, ApplicationProperties properties) {
        this.quizDao = quizDao;
        this.resultDao = resultDao;
        this.properties = properties;
    }

    @Override
    public Quiz getQuiz() {
        return quizDao.getQuiz();
    }

    @Override
    public Result processQuizSession(Session session) {
        var isPassed = isPassed(session);
        return new Result(
                session,
                isPassed
        );
    }

    @Override
    public void saveResult(Result result) {
        resultDao.saveResult(result);
    }

    @Override
    public List<Result> getResults() {
        return resultDao.getResults();
    }

    private boolean isPassed(Session session) {
        var quiz = session.getQuiz();
        var questions = quiz.getQuestions();
        var answersStats = session.getAnswersStats();
        var totalQuestionsNumber = questions.size();
        var rightAnswersNumber = 0;
        for (Question question : questions) {
            var answers = question.getAnswers();
            var questionId = question.getId();
            var answerIdx = answersStats.get(questionId);
            var answer = answers.get(answerIdx - 1);
            rightAnswersNumber += answer.isCorrect() ? 1 : 0;
        }
        return properties.getThreshold() <= (rightAnswersNumber * 1.0 / totalQuestionsNumber);
    }
    
}
