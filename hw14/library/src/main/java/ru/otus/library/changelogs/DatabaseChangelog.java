package ru.otus.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;


@ChangeLog
public class DatabaseChangelog {

    private Author author;

    private Genre genre;

    private Book book;

    @ChangeSet(order = "001", id = "dropDb", author = "com.otus.library", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "com.otus.library")
    public void insertGenres(GenreRepository genreRepository) {
        genre = genreRepository.save(new Genre(1L, "adventure"));
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "com.otus.library")
    public void insertAuthors(AuthorRepository authorRepository) {
        author = authorRepository.save(
                new Author(1L, "Jules Verne")
        );
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "com.otus.library")
    public void insertBooks(BookRepository bookRepository) {
        book = bookRepository.save(
                new Book(1L, "The Desert of Ice", author, genre, null)
        );
    }

    @ChangeSet(order = "005", id = "insertComments", author = "com.otus.library")
    public void insertComments(CommentRepository commentRepository) {
        commentRepository.saveAll(
                List.of(
                        new Comment(1L, "Good book", book),
                        new Comment(2L, "Affordable price", book)
                )
        );
    }
}
