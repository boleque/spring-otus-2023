package ru.otus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Author {

    @Id
    private String id;

    private String name;

    public Author(String authorName) {
        this.name = authorName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                '}';
    }
}
