package ru.otus.quizapp.config;

import java.io.InputStream;
import java.io.PrintStream;


public interface ServiceStreamsProvider {

    PrintStream outputStream();

    InputStream inputStream();
}
