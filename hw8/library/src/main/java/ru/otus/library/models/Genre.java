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
public class Genre {

    @Id
    private String id;

    private String name;

    public Genre(String genreName) {
        this.name = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                '}';
    }
}
