package ru.otus.quizapp.domain;

import java.util.UUID;

public class Result {
    private final String id;

    private final Session session;

    private final boolean isPassed;

    public Result(Session session, boolean isPassed) {
        this.session = session;
        this.isPassed = isPassed;

        id = UUID.randomUUID().toString();
    }

    public Session getSession() {
        return session;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public String getId() {
        return id;
    }
}
