package ru.otus.quizapp.converters;

import ru.otus.quizapp.domain.Question;

public interface QuiestionConverter {
    String convertQuiestionToString(Question question);
}
