package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.library.controllers.BookController;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.dto.ReadBookDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void listBookPageTest() throws Exception {
        given(bookService.getAll()).willReturn(
                List.of(new ReadBookDto(1L, "Java for Dummies", "Barry A. Burd", "tech"))
        );
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("books-list"))
                .andExpect(model().attribute("books", hasSize(1)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void editBookPageTest() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Barry A. Burd"));
        List<GenreDto> genres = List.of(new GenreDto(1L, "tech"));
        ReadBookDto bookDto = new ReadBookDto(1L, "Java Dummies", "Barry A. Burd", "tech");

        given(authorService.getAll()).willReturn(authors);
        given(genreService.getAll()).willReturn(genres);
        given(bookService.getById(1L)).willReturn(bookDto);

        mockMvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("book-save"))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(model().attribute("genres", hasSize(1)))
                .andExpect(model().attribute("authors", hasSize(1)));
    }

    @Test
    public void listBookPageTestUnauthorized() throws Exception {
        given(bookService.getAll()).willReturn(
                List.of(new ReadBookDto(1L, "Java for Dummies", "Barry A. Burd", "tech"))
        );
        mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
    }

    @Test
    public void editBookPageTestUnauthorized() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Barry A. Burd"));
        List<GenreDto> genres = List.of(new GenreDto(1L, "tech"));
        ReadBookDto bookDto = new ReadBookDto(1L, "Java Dummies", "Barry A. Burd", "tech");

        given(authorService.getAll()).willReturn(authors);
        given(genreService.getAll()).willReturn(genres);
        given(bookService.getById(1L)).willReturn(bookDto);

        mockMvc.perform(get("/edit?id=1")).andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void addBookPageTest() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Barry A. Burd"));
        List<GenreDto> genres = List.of(new GenreDto(1L, "tech"));
        ReadBookDto bookDto = new ReadBookDto();

        given(authorService.getAll()).willReturn(authors);
        given(genreService.getAll()).willReturn(genres);
        given(bookService.getById(1L)).willReturn(bookDto);

        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("book-save"))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(model().attribute("genres", hasSize(1)))
                .andExpect(model().attribute("authors", hasSize(1)));
    }

    @Test
    public void addBookPageTestUnauthorized() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Barry A. Burd"));
        List<GenreDto> genres = List.of(new GenreDto(1L, "tech"));
        ReadBookDto bookDto = new ReadBookDto();

        given(authorService.getAll()).willReturn(authors);
        given(genreService.getAll()).willReturn(genres);
        given(bookService.getById(1L)).willReturn(bookDto);

        mockMvc.perform(get("/add")).andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void deleteBookPageTest() throws Exception {
        mockMvc.perform(get("/delete?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void deleteBookPageTestUnauthorized() throws Exception {
        mockMvc.perform(get("/delete?id=1")).andExpect(status().isUnauthorized());
    }
}
