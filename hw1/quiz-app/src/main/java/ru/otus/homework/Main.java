package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.domain.Quiz;
import ru.otus.homework.service.CsvQuizService;
import ru.otus.homework.service.QuizService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizService service = context.getBean(CsvQuizService.class);
        Quiz quiz = service.getQuiz();
        System.out.println(quiz.toString());
        context.close();
    }
}