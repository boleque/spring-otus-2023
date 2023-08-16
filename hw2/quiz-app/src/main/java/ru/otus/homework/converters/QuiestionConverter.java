package ru.otus.homework.converters;

import ru.otus.homework.domain.Question;

public interface QuiestionConverter {
    String convertQuiestionToString(Question question);
}
