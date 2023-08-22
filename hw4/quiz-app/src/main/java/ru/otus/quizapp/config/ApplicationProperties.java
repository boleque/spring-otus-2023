package ru.otus.quizapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;


@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private final Locale locale;

    private final double threshold;

    @ConstructorBinding
    public ApplicationProperties(double threshold, String locale) {
        this.threshold = threshold;
        this.locale = Locale.forLanguageTag(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public double getThreshold() {
        return threshold;
    }
}
