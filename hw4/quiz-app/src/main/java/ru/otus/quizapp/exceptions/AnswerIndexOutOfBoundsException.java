package ru.otus.quizapp.exceptions;

public class AnswerIndexOutOfBoundsException extends IndexOutOfBoundsException {

    private final String message = "answerNumberOutOfRange";

    public AnswerIndexOutOfBoundsException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
