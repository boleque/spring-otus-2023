package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.BookConverter;
import ru.otus.library.domain.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;

@Component
public class GetBookByTitleCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final BookConverter converter;

    private final IOService ioService;

    private final ShellOption getBookByTitleMenuOption;

    public GetBookByTitleCommandProcessor(
            BookService bookService,
            IOService ioService,
            BookConverter converter,
            ShellOption getBookByTitleMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.converter = converter;
        this.getBookByTitleMenuOption = getBookByTitleMenuOption;
    }

    @Override
    public void processCommand() {
        String bookTitle = ioService.readStringWithPrompt("Input book title");
        Book book = bookService.getByTitle(bookTitle);
        ioService.outputString(converter.convertBookToString(book));
    }

    @Override
    public ShellOption getProcessedCommandOption() {
        return getBookByTitleMenuOption;
    }
}
