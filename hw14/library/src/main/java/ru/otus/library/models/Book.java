package ru.otus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book {

    @Id
    private long id;

    private String title;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    @DBRef
    private List<Comment> comments;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id = '" + id + '\'' +
                ", title = " + title +
                ", author = " + author.getName() +
                ", genre = " + genre.getName() +
                ", comments = '" + comments.stream().map(Object::toString).collect(Collectors.joining(", ")) + '\'' +
                '}';
    }
}
