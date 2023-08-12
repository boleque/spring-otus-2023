package ru.otus.homework.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class QuizCsvLoader implements QuizLoader {

    static final String DELIMITER = ";";

    static final int QUESTION_ID_IDX = 0;

    static final int QUESTION_TEXT_IDX = 1;

    static final int ANSWERS_SHIFT = 2;

    static final int ANSWER_1_TEXT_IDX = 2;

    static final int ANSWER_2_TEXT_IDX = 3;

    static final int ANSWER_3_TEXT_IDX = 4;

    static final int TRUE_ANSWER_ID_IDX = 5;

    private final Resource resource;

    public QuizCsvLoader(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Quiz getQuiz() {
        try {
            var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            var quizId = reader.readLine();
            var quizTopic = reader.readLine();
            List<Question> questions = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] questionData = line.split(DELIMITER);
                questions.add(parseQuestion(questionData, quizId));
            }
            return new Quiz(quizId, quizTopic, questions);
        } catch (IOException | IndexOutOfBoundsException ex) {
            throw new RuntimeException("Failed to parse quiz file source");
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
