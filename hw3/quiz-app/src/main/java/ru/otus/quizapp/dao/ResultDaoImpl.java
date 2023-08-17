package ru.otus.quizapp.dao;

import org.springframework.stereotype.Repository;
import ru.otus.quizapp.domain.Result;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ResultDaoImpl implements ResultDao {

    private final Map<String, Result> results;

    public ResultDaoImpl() {
        this.results = new HashMap<>();
    }

    @Override
    public Result getResult(String id) {
        return results.get(id);
    }

    @Override
    public void saveResult(Result result) {
        results.put(result.getId(), result);
    }
}
