package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookDto {

    private Long id;

    private String title;

    private Long authorId;

    private Long genreId;
}
