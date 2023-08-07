package ru.otus.homework.dao;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CsvQuizDao implements QuizDao {

    static final String DELIMITER = ";";

    static final int QUESTION_ID_IDX = 0;

    static final int QUESTION_TEXT_IDX = 1;

    static final int ANSWERS_SHIFT = 2;

    static final int ANSWER_1_TEXT_IDX = 2;

    static final int ANSWER_2_TEXT_IDX = 3;

    static final int ANSWER_3_TEXT_IDX = 4;

    static final int TRUE_ANSWER_ID_IDX = 5;

    private final ResourceLoader resourceLoader;

    private final String classpath;

    public CsvQuizDao(ResourceLoader resourceLoader, String classpath) {
        this.resourceLoader = resourceLoader;
        this.classpath = classpath;
    }

    @Override
    public Quiz getQuiz() {
        Resource resource = resourceLoader.getResource(String.format("classpath:%s", classpath));
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            String quizId = reader.readLine();
            String quizTopic = reader.readLine();
            List<Question> questions = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] questionData = line.split(DELIMITER);
                questions.add(parseQuestion(questionData, quizId));
            }
            return new Quiz(quizId, quizTopic, questions);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse quiz file source");
        }
    }

    private Question parseQuestion(String[] questionData, String quizId) {
        List<Answer> answers = Arrays.asList(
                parseAnswer(questionData, ANSWER_1_TEXT_IDX),
                parseAnswer(questionData, ANSWER_2_TEXT_IDX),
                parseAnswer(questionData, ANSWER_3_TEXT_IDX)
        );
        String questionUniqueId = questionData[QUESTION_ID_IDX];
        String questionText = questionData[QUESTION_TEXT_IDX];
        return new Question(questionUniqueId, quizId, questionText, answers);
    }

    private Answer parseAnswer(String[] questionData, int answerIdx) {
        String questionId = questionData[QUESTION_ID_IDX];
        int answerOrderId = answerIdx - ANSWERS_SHIFT;
        int trueAnswerId = Integer.parseInt(questionData[TRUE_ANSWER_ID_IDX]);
        String answerUniqueId = String.format("%s-%d", questionId, answerIdx);
        String answerText = questionData[answerIdx];
        return new Answer(answerUniqueId, questionId, answerText, answerOrderId == trueAnswerId);
    }
}
