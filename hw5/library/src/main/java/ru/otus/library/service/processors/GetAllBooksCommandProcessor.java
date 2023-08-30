package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.BookConverter;
import ru.otus.library.domain.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.shell.ShellOption;

import java.util.List;

@Component
public class GetAllBooksCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final BookConverter converter;

    private final IOService ioService;

    private final ShellOption getAllBooksMenuOption;

    public GetAllBooksCommandProcessor(
            BookService bookService,
            IOService ioService,
            BookConverter converter,
            ShellOption getAllBooksMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.converter = converter;
        this.getAllBooksMenuOption = getAllBooksMenuOption;
    }

    @Override
    public void processCommand() {
        List<Book> books = bookService.getAll();
        String result = converter.convertBookToString(books);
        ioService.outputString(result);
    }

    @Override
    public ShellOption getProcessedCommandOption() {
        return getAllBooksMenuOption;
    }
}
