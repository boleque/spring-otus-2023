package ru.otus.quizapp.domain;

import java.util.List;

public class Question {
    private final String id;

    private final String quizId;

    private final String text;

    private final List<Answer> answers;

    public Question(String id, String quizId, String text, List<Answer> answers) {
        this.id = id;
        this.quizId = quizId;
        this.text = text;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quizId='" + quizId + '\'' +
                ", text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
