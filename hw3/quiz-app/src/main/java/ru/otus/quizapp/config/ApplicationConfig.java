package ru.otus.quizapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfig {

    @Bean
    public Resource resource(@Value("${application.resource}") String resourceName) {
        var resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(String.format("classpath:%s", resourceName));
    }

    @Bean
    public LocaleProvider localeProvider(@Value("${application.locale}") Locale locale) {
        return new DefaultLocaleProvider(locale);
    }

}
