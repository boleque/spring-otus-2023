package ru.otus.quizapp.config;

import java.util.Locale;

public class DefaultLocaleProvider implements LocaleProvider {

    private final Locale locale;

    public DefaultLocaleProvider(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getCurrent() {
        return locale;
    }
}
