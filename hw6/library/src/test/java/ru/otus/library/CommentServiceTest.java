package ru.otus.library;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.CommentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CommentServiceImpl.class)
@ExtendWith({SpringExtension.class})
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    public void requestNonExistingCommentById() {
        given(commentRepository.getCommentById(anyLong())).willReturn(
                Optional.empty()
        );
        assertThrows(EntityNotFoundException.class,
                () -> commentService.getCommentById(anyLong())
        );
    }

}
