package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.CommentConverter;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Comment;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

@Component
public class GetCommentByIdCommandProcessor implements CommandProcessor {

    private final CommentService commentService;

    private final CommentConverter converter;

    private final IOService ioService;

    private final MenuOption getCommentByIdMenuOption;

    public GetCommentByIdCommandProcessor(
            CommentService commentService,
            IOService ioService,
            CommentConverter converter,
            MenuOption getCommentByIdMenuOption) {
        this.commentService = commentService;
        this.ioService = ioService;
        this.converter = converter;
        this.getCommentByIdMenuOption = getCommentByIdMenuOption;
    }

    @Override
    public void processCommand() {
        long commentId = ioService.readLongWithPrompt("Input comment id");
        try {
            Comment comment = commentService.getCommentById(commentId);
            ioService.outputString(converter.convertCommentToString(comment));
        } catch (EntityNotFoundException ex) {
            ioService.outputString(ex.toString());
        }
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return getCommentByIdMenuOption;
    }
}
