package ru.otus.spring.integration.services;

import ru.otus.spring.integration.domain.AgeRestriction;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static void makeDelay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static AgeRestriction getRandomAgeRestriction() {
        return AgeRestriction.values()[ThreadLocalRandom.current().nextInt(AgeRestriction.values().length)];
    }

}
