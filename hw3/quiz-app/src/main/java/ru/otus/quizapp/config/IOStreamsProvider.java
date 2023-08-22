package ru.otus.quizapp.config;

import java.io.PrintStream;
import java.util.Scanner;


public interface IOStreamsProvider {

    PrintStream outputStream();

    Scanner inputStream();
}
