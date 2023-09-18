package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;


@Component
public class UpdateBookTitleCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final IOService ioService;

    private final MenuOption updateBookTitleMenuOption;

    public UpdateBookTitleCommandProcessor(
            BookService bookService,
            IOService ioService,
            MenuOption updateBookTitleMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.updateBookTitleMenuOption = updateBookTitleMenuOption;
    }

    @Override
    public void processCommand() {
        String bookId = ioService.readStringWithPrompt("Book id");
        String newTitle = ioService.readStringWithPrompt("New book title");
        try {
            bookService.updateBookTitle(bookId, newTitle);
            ioService.outputString("Book title is successfully update");
        } catch (EntityNotFoundException ex) {
            ioService.outputString(ex.toString());
        }
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return this.updateBookTitleMenuOption;
    }
}
