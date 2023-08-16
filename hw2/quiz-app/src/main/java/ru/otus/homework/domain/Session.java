package ru.otus.homework.domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class Session {
    private final String id;

    private final User user;

    private final LocalDateTime date;

    private final Quiz quiz;

    private final Map<String, Integer> answersStats;

    public Session(User user, Quiz quiz, Map<String, Integer> answersStats) {
        this.user = user;
        this.quiz = quiz;
        this.answersStats = answersStats;

        id = UUID.randomUUID().toString();
        date = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getAnswersStats() {
        return answersStats;
    }
}
