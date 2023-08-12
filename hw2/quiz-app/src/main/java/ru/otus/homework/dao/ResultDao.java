package ru.otus.homework.dao;

import ru.otus.homework.domain.Result;

public interface ResultDao {

    Result getResult(String id);

    void saveResult(Result result);

}
