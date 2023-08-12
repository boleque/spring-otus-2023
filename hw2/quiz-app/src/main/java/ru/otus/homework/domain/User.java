package ru.otus.homework.domain;

import java.util.UUID;

public class User {

    private final String id;

    private final String firstName;

    private final String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        id = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }
}
