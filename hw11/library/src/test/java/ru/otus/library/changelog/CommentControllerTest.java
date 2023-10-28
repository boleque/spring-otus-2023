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
import ru.otus.library.dto.CommentDto;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    @MockBean
    CommentRepository commentRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAllComments() {
        final Book book = new Book("1", "bookTitle", null, null, null);
        when(commentRepository.findAll()).thenReturn(Flux.just(
                new Comment("1", "good", book),
                new Comment("2", "bad", book),
                new Comment("3", "neutral", book)
        ));

        webTestClient.get()
                .uri("/comments/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(3);
    }
}
