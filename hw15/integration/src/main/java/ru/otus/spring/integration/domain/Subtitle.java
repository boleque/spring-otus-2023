package ru.otus.spring.integration.domain;


public class Subtitle {

    private final String originalText;

    private final String censoredText;

    public Subtitle(String originalText, String censoredText) {
        this.originalText = originalText;
        this.censoredText = censoredText;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getCensoredText() {
        return censoredText;
    }
}
