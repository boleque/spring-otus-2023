package ru.otus.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.namedParameterJdbcOperations = jdbc;
    }

    @Override
    public void create(String title, long authorId, long genreId) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("author_id", authorId)
                .addValue("genre_id", genreId);
        namedParameterJdbcOperations.update(
                "insert into book (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                paramMap
        );
    }

    @Override
    public void update(long id, String title, long authorId, long genreId) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("title", title)
                .addValue("author_id", authorId)
                .addValue("genre_id", genreId);
        namedParameterJdbcOperations.update(
                "update book set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                paramMap
        );
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("id", id);
        namedParameterJdbcOperations.update("delete from book where id = :id", paramMap);
    }

    @Override
    public Book getById(long id) {
        SqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select b.id, b.title, a.name as author, g.name as genre" +
                     " from book as b" +
                     " inner join author as a on b.author_id = a.id" +
                     " inner join genre as g on b.genre_id = g.id " +
                     " where b.id = :id",
                paramMap, new BookRowMapper());
    }

    @Override
    public Book getByTitle(String title) {
        SqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("title", "%" + title + "%");
        return namedParameterJdbcOperations.queryForObject(
                "select b.id, b.title, a.name as author, g.name as genre " +
                        "from book as b " +
                        "inner join author as a on a.id = b.author_id " +
                        "inner join genre as g on g.id = b.genre_id " +
                        "where b.title like :title",
                paramMap, new BookRowMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, a.name as author, g.name as genre " +
                     "from book as b " +
                     "inner join author as a on a.id = b.author_id " +
                     "inner join genre as g on g.id = b.genre_id " +
                     "order by b.title",
                new BookRowMapper());
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            String genre = rs.getString("genre");
            return new Book(id, title, author, genre);
        }
    }
}
