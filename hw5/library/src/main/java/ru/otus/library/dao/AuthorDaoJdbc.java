package ru.otus.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.namedParameterJdbcOperations = jdbc;
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select id, name from author", new AuthorRowMapper());
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long authorId = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(authorId, name);
        }
    }
}
