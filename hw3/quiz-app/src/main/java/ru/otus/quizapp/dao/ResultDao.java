package ru.otus.quizapp.dao;


import ru.otus.quizapp.domain.Result;

public interface ResultDao {

    Result getResult(String id);

    void saveResult(Result result);

}
