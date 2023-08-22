package ru.otus.quizapp.dao;

import ru.otus.quizapp.domain.Result;
import java.util.List;

public interface ResultDao {

    List<Result> getResults();

    void saveResult(Result result);

}
