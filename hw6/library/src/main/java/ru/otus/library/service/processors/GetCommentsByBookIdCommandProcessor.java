package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.CommentConverter;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Comment;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

import java.util.List;

@Component
public class GetCommentsByBookIdCommandProcessor implements CommandProcessor {

    private final CommentService commentService;

    private final CommentConverter converter;

    private final IOService ioService;

    private final MenuOption getCommentByBookIdMenuOption;

    public GetCommentsByBookIdCommandProcessor(
            CommentService commentService,
            IOService ioService,
            CommentConverter converter,
            MenuOption getCommentByBookIdMenuOption) {
        this.commentService = commentService;
        this.ioService = ioService;
        this.converter = converter;
        this.getCommentByBookIdMenuOption = getCommentByBookIdMenuOption;
    }

    @Override
    public void processCommand() {
        long bookId = ioService.readLongWithPrompt("Input book id");
        try {
            List<Comment> comments = commentService.getAllCommentsByBookId(bookId);
            ioService.outputString(converter.convertCommentsToString(comments));
        } catch (EntityNotFoundException ex) {
            ioService.outputString(ex.toString());
        }
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return getCommentByBookIdMenuOption;
    }
}
