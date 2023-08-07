package ru.otus.homework.domain;

public class Answer {
    private final String id;

    private final String questionId;

    private final String text;

    private final boolean isCorrect;

    public Answer(String id, String questionId, String text, boolean isCorrect) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId='" + questionId + '\'' +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
