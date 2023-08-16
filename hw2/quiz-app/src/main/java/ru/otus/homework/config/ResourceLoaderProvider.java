package ru.otus.homework.config;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public interface ResourceLoaderProvider {

    Resource resource(String resourceName);

    ResourceLoader resourceLoader();
}
