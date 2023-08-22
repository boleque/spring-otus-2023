package ru.otus.quizapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.quizapp.config.ApplicationProperties;
import ru.otus.quizapp.config.ApplicationConfig;
import ru.otus.quizapp.config.DefaultLocaleProvider;
import ru.otus.quizapp.config.LocaleProvider;
import ru.otus.quizapp.dao.QuizDaoImpl;
import ru.otus.quizapp.dao.ResultDao;
import ru.otus.quizapp.dao.ResultDaoImpl;
import ru.otus.quizapp.service.*;

import java.util.Locale;

@Component
public class QuizTestConfig {

    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties(false, 0.6, "ru_RU");
    }

    @Bean
    public ApplicationConfig ApplicationConfig() {
        return new ApplicationConfig();
    }

    @Bean
    public ResultDaoImpl resultDao() {
        return new ResultDaoImpl();
    }

    @Bean
    public Locale locale() {
        return new Locale("ru-RU");
    }

    @Bean
    public LocaleProvider localeProvider(Locale locale) {
        return new DefaultLocaleProvider(locale);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        var source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        return source;
    }

    @Bean
    public LocalizationService localizationService(
            LocaleProvider localeProvider,
            ResourceBundleMessageSource messageSource
    ) {
        return new LocalizationServiceImpl(localeProvider, messageSource);
    }

    @Bean
    public QuizParser quizParser(LocalizationService localizationService) {
        return new QuizParserImpl(localizationService);
    }

    @Bean
    public Resource resource() {
        var resourceName = "world_geography.csv";
        var resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(String.format("classpath:%s", resourceName));
    }

    @Bean
    public QuizCsvLoader quizCsvLoader(Resource resource, QuizParser quizParser, LocalizationService localizationService) {
        return new QuizCsvLoader(resource, quizParser, localizationService);
    }

    @Bean
    public QuizDaoImpl quizDao(QuizCsvLoader quizCsvLoader) {
        return new QuizDaoImpl(quizCsvLoader);
    }

    @Bean
    public QuizService quizService(QuizDaoImpl quizDao, ResultDao resultDao, ApplicationProperties applicationProperties) {
        return new QuizServiceImpl(quizDao, resultDao, applicationProperties);
    }
}
