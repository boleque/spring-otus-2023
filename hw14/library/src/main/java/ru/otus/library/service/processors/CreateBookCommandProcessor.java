package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;


@Component
public class CreateBookCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final IOService ioService;

    private final MenuOption createBookMenuOption;

    public CreateBookCommandProcessor(
            BookService bookService,
            IOService ioService,
            MenuOption createBookMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.createBookMenuOption = createBookMenuOption;
    }

    @Override
    public void processCommand() {
        String bookTitle = ioService.readStringWithPrompt("Book title");
        String bookAuthorId = ioService.readStringWithPrompt("Book author");
        String bookGenreId = ioService.readStringWithPrompt("Book genre");
        try {
            bookService.createBook(bookTitle, bookAuthorId, bookGenreId);
            ioService.outputString("Book is successfully created");
        } catch (EntityNotFoundException ex) {
            ioService.outputString(ex.toString());
        }
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return this.createBookMenuOption;
    }
}
