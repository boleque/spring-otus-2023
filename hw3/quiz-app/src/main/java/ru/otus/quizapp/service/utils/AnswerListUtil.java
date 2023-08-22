package ru.otus.quizapp.service.utils;

import ru.otus.quizapp.exceptions.AnswerIndexOutOfBoundsException;

public class AnswerListUtil {
    public static void checkAnswerNumber(int answerNumber, int answersCount) {
        if (answerNumber <= 0 || answerNumber > answersCount) {
            throw new AnswerIndexOutOfBoundsException("Given number of an answer is out of range");
        }
    }
}
