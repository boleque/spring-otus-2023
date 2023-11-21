package ru.otus.spring.integration.domain;

public class PhraseItem {

    private final AgeRestriction ageRestriction;

    private final String text;

    public PhraseItem(AgeRestriction ageRestriction, String text) {
        this.ageRestriction = ageRestriction;
        this.text = text;
    }


    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public String getText() {
        return text;
    }
}
