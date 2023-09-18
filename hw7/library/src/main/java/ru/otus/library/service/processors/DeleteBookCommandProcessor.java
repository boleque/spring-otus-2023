package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

@Component
public class DeleteBookCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final IOService ioService;

    private final MenuOption deleteBookMenuOption;

    public DeleteBookCommandProcessor(
            BookService bookService,
            IOService ioService,
            MenuOption deleteBookMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.deleteBookMenuOption = deleteBookMenuOption;
    }

    @Override
    public void processCommand() {
        long bookId = ioService.readLongWithPrompt("Input book id");
        bookService.deleteById(bookId);
        ioService.outputString("Book is successfully deleted");
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return deleteBookMenuOption;
    }
}
