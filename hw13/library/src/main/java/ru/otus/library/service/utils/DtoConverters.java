package ru.otus.library.service.utils;

import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.dto.ReadBookDto;
import ru.otus.library.dto.ReadCommentDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;


public class DtoConverters {

    public static ReadBookDto convertToReadBookDto(Book book) {
        ReadBookDto bookDto = new ReadBookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthorName(book.getAuthor().getName());
        bookDto.setGenreName(book.getGenre().getName());

        return bookDto;
    }

    public static AuthorDto convertToAuthorDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());

        return authorDto;
    }

    public static GenreDto convertToGenreDto(Genre genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        return genreDto;
    }

    public static ReadCommentDto convertToReadCommentDto(Comment comment) {
        ReadCommentDto readCommentDto = new ReadCommentDto();
        readCommentDto.setId(comment.getId());
        readCommentDto.setText(comment.getText());
        readCommentDto.setBookTitle(comment.getBook().getTitle());

        return readCommentDto;
    }
}
