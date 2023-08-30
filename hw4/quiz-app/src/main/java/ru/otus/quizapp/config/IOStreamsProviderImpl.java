package ru.otus.quizapp.config;


import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;


@Component
public class IOStreamsProviderImpl implements IOStreamsProvider {

    private final PrintStream outputStream = System.out;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public PrintStream outputStream() {
        return outputStream;
    }

    @Override
    public Scanner inputStream() {
        return scanner;
    }
}
