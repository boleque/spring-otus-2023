package ru.otus.quizapp.dao;

import org.springframework.stereotype.Component;
import ru.otus.quizapp.domain.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class ResultDaoImpl implements ResultDao {

    private final Map<String, Result> results;

    public ResultDaoImpl() {
        this.results = new HashMap<>();
    }

    @Override
    public List<Result> getResults() {
        return new ArrayList<>(results.values());
    }

    @Override
    public void saveResult(Result result) {
        results.put(result.getId(), result);
    }
}
