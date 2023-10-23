package ru.otus.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.otus.library.controllers.BookController;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnCorrectBooksList() throws Exception {
        final List<BookDto> books = List.of(
                new BookDto(1L, "Java for Dummies", new AuthorDto(1L, "Barry A. Burd"), new GenreDto(1L, "tech")),
                new BookDto(2L, "Kotlin for Dummies", new AuthorDto(1L, "Jan Weinschenker"), new GenreDto(1L, "tech"))
        );
        final String expectedResult = "[{\"id\":1,\"title\":\"Java for Dummies\",\"author\":{\"id\":1,\"name\":\"Barry A. Burd\"},\"genre\":{\"id\":1,\"name\":\"tech\"}},{\"id\":2,\"title\":\"Kotlin for Dummies\",\"author\":{\"id\":1,\"name\":\"Jan Weinschenker\"},\"genre\":{\"id\":1,\"name\":\"tech\"}}]";

        given(bookService.getAll()).willReturn(books);
        mockMvc.perform(get("/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    public void shouldReturnCorrectBookByIdInPath() throws Exception {
        final long bookId = 1L;
        final BookDto book = new BookDto(1L, "Java for Dummies", new AuthorDto(1L, "Barry A. Burd"), new GenreDto(1L, "tech"));
        final String expectedResult = "{\"id\":1,\"title\":\"Java for Dummies\",\"author\":{\"id\":1,\"name\":\"Barry A. Burd\"},\"genre\":{\"id\":1,\"name\":\"tech\"}}";

        given(bookService.getById(bookId)).willReturn(book);
        mockMvc.perform(get(String.format("/books/%s", bookId)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    public void shouldEditCorrectBookByRequestBody() throws Exception {
        final BookDto book = new BookDto(1L, "Java for Dummies", new AuthorDto(1L, "Barry A. Burd"), new GenreDto(1L, "tech"));
        final String request = mapper.writeValueAsString(book);
        final String expectedResult = "{\"id\":1,\"title\":\"Java for Dummies\",\"author\":{\"id\":1,\"name\":\"Barry A. Burd\"},\"genre\":{\"id\":1,\"name\":\"tech\"}}";

        given(bookService.saveBook(any())).willReturn(book);
        mockMvc.perform(put("/books")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(expectedResult));
    }

    @Test
    public void shouldAddCorrectBookByRequestBody() throws Exception {
        final BookDto book = new BookDto(1L, "Java for Dummies", new AuthorDto(1L, "Barry A. Burd"), new GenreDto(1L, "tech"));
        final String request = mapper.writeValueAsString(book);
        final String expectedResult = "{\"id\":1,\"title\":\"Java for Dummies\",\"author\":{\"id\":1,\"name\":\"Barry A. Burd\"},\"genre\":{\"id\":1,\"name\":\"tech\"}}";

        given(bookService.saveBook(any())).willReturn(book);
        mockMvc.perform(post("/books")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    public void shouldDeleteCorrectBookById() throws Exception {
        final long bookId = 1L;
        mockMvc.perform(delete(String.format("/books/%s", bookId)))
                .andExpect(status().isOk());
    }
}
