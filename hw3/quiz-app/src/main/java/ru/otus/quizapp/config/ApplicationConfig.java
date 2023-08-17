package ru.otus.quizapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfig implements
        ResourceLoaderProvider,
        ServiceStreamsProvider {

    @Bean
    @Override
    public Resource resource(@Value("${application.resource}") String resourceName) {
        var resourceLoader = resourceLoader();
        return resourceLoader.getResource(String.format("classpath:%s", resourceName));
    }

    @Bean
    public LocaleProvider localeProvider(@Value("${application.locale}") Locale locale) {
        return new DefaultLocaleProvider(locale);
    }

    @Bean
    @Override
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }

    @Bean
    @Override
    public PrintStream outputStream() {
        return System.out;
    }

    @Bean
    @Override
    public InputStream inputStream() {
        return System.in;
    }


}
