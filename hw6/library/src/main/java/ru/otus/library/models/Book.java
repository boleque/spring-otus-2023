package ru.otus.library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(
        name = "book-author-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")}
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "book_id", updatable = false)
    private List<Comment> comments;

    public Book(String title, Author author, Genre genre) {
        this.id = 0;
        this.author = author;
        this.title = title;
        this.genre = genre;
    }
}
