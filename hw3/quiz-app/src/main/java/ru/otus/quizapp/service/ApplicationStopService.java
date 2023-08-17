package ru.otus.quizapp.service;

public interface ApplicationStopService {

    boolean isApplicationRunning();

    void stopApplication();
}
