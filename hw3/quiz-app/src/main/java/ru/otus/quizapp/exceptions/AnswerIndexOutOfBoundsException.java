package ru.otus.quizapp.exceptions;

public class AnswerIndexOutOfBoundsException extends IndexOutOfBoundsException {

    private final static String MESSAGE = "answerNumberOutOfRange";

    public AnswerIndexOutOfBoundsException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
