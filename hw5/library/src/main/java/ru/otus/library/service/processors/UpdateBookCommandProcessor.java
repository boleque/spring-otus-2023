package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;

@Component
public class UpdateBookCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final IOService ioService;

    private final ShellOption updateBookMenuOption;

    public UpdateBookCommandProcessor(
            BookService bookService,
            IOService ioService,
            ShellOption updateBookMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.updateBookMenuOption = updateBookMenuOption;
    }

    @Override
    public void processCommand() {
        long bookId = ioService.readLongWithPrompt("Input book id");
        String title = ioService.readStringWithPrompt("Input title");
        long authorId = ioService.readLongWithPrompt("Input author id");
        long genreId = ioService.readLongWithPrompt("Input genre id");

        bookService.update(bookId, title, authorId, genreId);
        ioService.outputString("Book is successfully updated");
    }

    @Override
    public ShellOption getProcessedCommandOption() {
        return updateBookMenuOption;
    }
}
