package ru.otus.library.configuration;

import java.io.PrintStream;
import java.util.Scanner;


public interface IOStreamsProvider {

    PrintStream outputStream();

    Scanner inputStream();
}
