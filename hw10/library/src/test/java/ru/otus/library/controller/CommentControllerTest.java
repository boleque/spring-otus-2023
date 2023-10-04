package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.controllers.CommentController;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.service.CommentService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void shouldReturnCorrectCommentsList() throws Exception {
        final List<CommentDto> comments = List.of(
                new CommentDto(1L, "Good book", "Java for Dummies"),
                new CommentDto(2L, "Perfect book", "Kotlin for Dummies")
        );
        final String expectedResult = "[{\"id\":1,\"text\":\"Good book\",\"bookTitle\":\"Java for Dummies\"},{\"id\":2,\"text\":\"Perfect book\",\"bookTitle\":\"Kotlin for Dummies\"}]";

        given(commentService.getAll()).willReturn(comments);
        mockMvc.perform(get("/comments/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}
