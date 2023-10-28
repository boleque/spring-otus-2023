package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadBookDto {

    private Long id;

    private String title;

    private String authorName;

    private String genreName;
}
