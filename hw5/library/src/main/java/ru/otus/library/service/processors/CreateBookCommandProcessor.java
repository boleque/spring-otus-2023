package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.BookConverter;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;


@Component
public class CreateBookCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final IOService ioService;

    private final ShellOption createBookMenuOption;

    public CreateBookCommandProcessor(
            BookService bookService,
            IOService ioService,
            BookConverter converter,
            ShellOption createBookMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.createBookMenuOption = createBookMenuOption;
    }

    @Override
    public void processCommand() {
        String bookTitle = ioService.readStringWithPrompt("Book title");
        long bookAuthorId = ioService.readLongWithPrompt("Book author");
        long bookGenreId = ioService.readLongWithPrompt("Book genre");

        bookService.create(bookTitle, bookAuthorId, bookGenreId);
        ioService.outputString("Book is successfully created");
    }

    @Override
    public ShellOption getProcessedCommandOption() {
        return this.createBookMenuOption;
    }
}
