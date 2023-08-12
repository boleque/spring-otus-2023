package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;


@Component
@PropertySource("classpath:app.properties")
public class AppSettings implements
        QuizThresholdProvider,
        ApplicationStopServiceSettingsProvider,
        ResourceLoaderProvider,
        ServiceStreamsProvider {

    private final boolean confirmExit;

    private final double threshold;

    public AppSettings(
            @Value("${app.confirmExit}") boolean confirmExit,
            @Value("${quiz.threshold}") double threshold) {
        this.confirmExit = confirmExit;
        this.threshold = threshold;
    }

    @Override
    public boolean isConfirmExit() {
        return confirmExit;
    }

    @Override
    public double getThreshold() {
        return threshold;
    }

    @Bean
    @Override
    public Resource resource(@Value("${resource.name}") String resourceName) {
        var resourceLoader = resourceLoader();
        return resourceLoader.getResource(String.format("classpath:%s", resourceName));
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
