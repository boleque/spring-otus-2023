package ru.otus.quizapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;


@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private final boolean confirmExit;

    private final Locale locale;

    private final double threshold;

    @ConstructorBinding
    public ApplicationProperties(boolean confirmExit, double threshold, String locale) {
        this.confirmExit = confirmExit;
        this.threshold = threshold;
        this.locale = Locale.forLanguageTag(locale);
    }

    public boolean isConfirmExit() {
        return confirmExit;
    }

    public Locale getLocale() {
        return locale;
    }

    public double getThreshold() {
        return threshold;
    }
}
