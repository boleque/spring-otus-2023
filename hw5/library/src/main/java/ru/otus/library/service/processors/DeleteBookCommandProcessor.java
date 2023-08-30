package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;

@Component
public class DeleteBookCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final IOService ioService;

    private final ShellOption deleteBookMenuOption;

    public DeleteBookCommandProcessor(
            BookService bookService,
            IOService ioService,
            ShellOption deleteBookMenuOption) {
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
    public ShellOption getProcessedCommandOption() {
        return deleteBookMenuOption;
    }
}
