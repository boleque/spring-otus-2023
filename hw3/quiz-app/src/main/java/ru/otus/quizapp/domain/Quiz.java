package ru.otus.quizapp.domain;

import java.util.List;

public class Quiz {
    private final String id;

    private final String topic;

    private final List<Question> questions;

    public Quiz(String id, String topic, List<Question> questions) {
        this.id = id;
        this.topic = topic;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", questions=" + questions +
                '}';
    }
}
