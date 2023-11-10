package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.library.dto.ReadCommentDto;
import ru.otus.library.service.CommentService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public String listCommentsPage(Model model) {
        List<ReadCommentDto> comments = commentService.getAll();
        model.addAttribute("comments", comments);
        return "comments-list";
    }
}
