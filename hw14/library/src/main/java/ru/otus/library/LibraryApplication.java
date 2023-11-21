package ru.otus.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties
public class LibraryApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LibraryApplication.class, args);
		Console.main(args);
	}
}
