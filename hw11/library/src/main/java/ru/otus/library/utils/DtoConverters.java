package ru.otus.library.utils;

import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;

import java.util.List;


public class DtoConverters {

    public static BookDto convertToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());

        bookDto.setAuthor(convertToAuthorDto(book.getAuthor()));
        bookDto.setGenre(convertToGenreDto(book.getGenre()));

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

    public static CommentDto convertToCommentDto(Comment comment) {
        CommentDto readCommentDto = new CommentDto();
        readCommentDto.setId(comment.getId());
        readCommentDto.setText(comment.getText());
        readCommentDto.setBookTitle(comment.getBook().getTitle());

        return readCommentDto;
    }

    public static BookCommentDto convertToBookCommentDto(Book book) {
        BookCommentDto bookCommentDto = new BookCommentDto();
        bookCommentDto.setBookId(book.getId());
        bookCommentDto.setBookTitle(book.getTitle());
        List<String> comments = book.getComments().stream().map(Comment::getText).toList();
        bookCommentDto.setComments(comments);

        return bookCommentDto;
    }
}
