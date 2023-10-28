package ru.otus.library.changelog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnBookRequestedById() {
        final Book book = new Book(
                "1",
                "title",
                new Author("1", "authorName"),
                new Genre("1", "genreName"),
                null
        );
        when(bookRepository.findById("1")).thenReturn(
                Mono.just(book)
        );

        webTestClient.get()
                .uri("/books/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getId, equalTo("1"));
    }

    @Test
    void shouldHandleRequestBookNotFoundById() {
        when(bookRepository.findById("1")).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/books/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnAllBooks() {
        final Author author = new Author("1", "authorName");
        final Genre genre = new Genre("1", "genreName");
        when(bookRepository.findAll()).thenReturn(Flux.just(
                new Book("1", "title1", author, genre, null),
                new Book("2", "title2", author, genre, null),
                new Book("3", "title3", author, genre, null)
        ));

        webTestClient.get()
                .uri("/books/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(3);
    }

    @Test
    void shouldShouldReturnBookComments() {
        final Book book = new Book("1", "title", null, null, null);
        final Comment comment = new Comment("1", "good", book);
        book.setComments(List.of(comment));
        when(bookRepository.findById("1")).thenReturn(
                Mono.just(book)
        );

        webTestClient.get()
                .uri("/books/1/comments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookCommentDto.class)
                .value(b -> b.getComments().size(), equalTo(1));
    }

    @Test
    void shouldCreateNewBook() {
        final Book book = new Book(
                "1",
                "newBook",
                new Author("1", "authorName"),
                new Genre("1", "genreName"),
                null
        );
        when(bookRepository.save(any())).thenReturn(
                Mono.just(book)
        );

        webTestClient.post()
                .uri("/books")
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getTitle, equalTo("newBook"));
    }

    @Test
    void shouldUpdateExistingBook() {
        final Book originalBook = new Book(
                "1",
                "originalBook",
                new Author("1", "authorName"),
                new Genre("1", "genreName"),
                null
        );
        when(bookRepository.findById("1")).thenReturn(
                Mono.just(originalBook)
        );
        final Book changedBook = new Book(
                "1",
                "changedBook",
                new Author("2", "changedAuthor"),
                new Genre("2", "changedGenre"),
                null
        );
        when(bookRepository.save(any())).thenReturn(
                Mono.just(changedBook)
        );

        webTestClient.put()
                .uri("/books")
                .bodyValue(changedBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(BookDto::getTitle, equalTo("changedBook"));
    }

    @Test
    void shouldDeleteBookById() {
        when(bookRepository.deleteById("1")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/books/1")
                .exchange()
                .expectStatus().isOk();
    }
}
