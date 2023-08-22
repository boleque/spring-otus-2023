package ru.otus.quizapp.service;

import org.springframework.stereotype.Service;
import ru.otus.quizapp.domain.Answer;
import ru.otus.quizapp.domain.Question;
import ru.otus.quizapp.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class QuizParserImpl implements QuizParser {

    private static final String DELIMITER = ";";

    private static final int QUESTION_ID_IDX = 0;

    private static final int QUESTION_TEXT_IDX = 1;

    private static final int ANSWERS_SHIFT = 2;

    private static final int ANSWER_1_TEXT_IDX = 2;

    private static final int ANSWER_2_TEXT_IDX = 3;

    private static final int ANSWER_3_TEXT_IDX = 4;

    private static final int TRUE_ANSWER_ID_IDX = 5;

    private final LocalizationService localizationService;

    public QuizParserImpl(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Override
    public Quiz parseQuiz(BufferedReader reader) {
        String line;
        try {
            var quizId = reader.readLine();
            var quizTopic = reader.readLine();
            List<Question> questions = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] questionData = line.split(DELIMITER);
                questions.add(parseQuestion(questionData, quizId));
            }
            return new Quiz(quizId, quizTopic, questions);
        } catch (IOException | IndexOutOfBoundsException ex) {
            var msg = localizationService.getMessage("failedToParseQuizResource");
            throw new RuntimeException(msg);
        }
    }

    private Question parseQuestion(String[] questionData, String quizId) {
        List<Answer> answers = Arrays.asList(
                parseAnswer(questionData, ANSWER_1_TEXT_IDX),
                parseAnswer(questionData, ANSWER_2_TEXT_IDX),
                parseAnswer(questionData, ANSWER_3_TEXT_IDX)
        );
        var questionUniqueId = questionData[QUESTION_ID_IDX];
        var questionText = questionData[QUESTION_TEXT_IDX];
        return new Question(questionUniqueId, quizId, questionText, answers);
    }

    private Answer parseAnswer(String[] questionData, int answerIdx) {
        var questionId = questionData[QUESTION_ID_IDX];
        var answerOrderId = answerIdx - ANSWERS_SHIFT;
        var trueAnswerId = Integer.parseInt(questionData[TRUE_ANSWER_ID_IDX]);
        var answerUniqueId = String.format("%s-%d", questionId, answerIdx);
        var answerText = questionData[answerIdx];
        return new Answer(answerUniqueId, questionId, answerText, answerOrderId == trueAnswerId);
    }

}
