package ru.otus.homework.service.utils;

import ru.otus.homework.exceptions.AnswerIndexOutOfBoundsException;

public class AnswerListUtil {
    public static void checkAnswerNumber(int answerNumber, int answersCount) {
        if (answerNumber <= 0 || answerNumber > answersCount) {
            throw new AnswerIndexOutOfBoundsException("Given number of an answer is out of range");
        }
    }
}
