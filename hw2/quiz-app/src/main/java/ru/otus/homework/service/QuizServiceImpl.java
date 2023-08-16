package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.config.QuizThresholdProvider;
import ru.otus.homework.dao.QuizDao;
import ru.otus.homework.dao.ResultDao;
import ru.otus.homework.domain.Quiz;
import ru.otus.homework.domain.Session;
import ru.otus.homework.domain.Result;
import ru.otus.homework.domain.Question;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    private final ResultDao resultDao;

    private final QuizThresholdProvider settings;

    public QuizServiceImpl(QuizDao quizDao, ResultDao resultDao, QuizThresholdProvider settings) {
        this.quizDao = quizDao;
        this.resultDao = resultDao;
        this.settings = settings;
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
            var answer = answers.get(answerIdx);
            rightAnswersNumber += answer.isCorrect() ? 1 : 0;
        }
        return settings.getThreshold() <= (rightAnswersNumber * 1.0 / totalQuestionsNumber);
    }
    
}
