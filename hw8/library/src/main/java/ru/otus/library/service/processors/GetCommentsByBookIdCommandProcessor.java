package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.CommentConverter;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Comment;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

import java.util.List;

@Component
public class GetCommentsByBookIdCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final CommentConverter converter;

    private final IOService ioService;

    private final MenuOption getCommentByBookIdMenuOption;

    public GetCommentsByBookIdCommandProcessor(
            BookService bookService,
            IOService ioService,
            CommentConverter converter,
            MenuOption getCommentByBookIdMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.converter = converter;
        this.getCommentByBookIdMenuOption = getCommentByBookIdMenuOption;
    }

    @Override
    public void processCommand() {
        String bookId = ioService.readStringWithPrompt("Input book id");
        try {
            List<Comment> comments = bookService.getAllCommentsByBookId(bookId);
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
